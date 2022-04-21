package com.yae.evaluation.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import com.yae.evaluation.RESTTemplates.AssignmentTemplate;
import com.yae.evaluation.RESTTemplates.UploadTemplate;
import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.repository.SubmissionRepository;
import com.yae.evaluation.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private Environment environment;

    public Submission findSubmissionById(Long id) {
        return submissionRepository.findSubmissionById(id);
    }

    public String getScore(Path tmp, Map<String, String> testCases) throws IOException{
        int score=0;
        int maxScore=0;
        String output = "";

        for(String testCaseInput: testCases.keySet()) 
        {
            String command = String.format("python %s", tmp);
			Process process = Runtime.getRuntime().exec(command);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(testCaseInput);
            writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String testCaseOutput = new String("");

			while ((line = reader.readLine()) != null) {
				testCaseOutput+= line+"\n";
			}

            String actual = testCaseOutput.strip();
            String out = testCases.get(testCaseInput).strip();
            if (actual.equals(out)) {
                score++;
                output += ("Output matches test case. +1\n");
            }

            else {
                output += ("Failed test case.\nExpect:" + out + "\nGot: " + testCaseOutput);
            }

            maxScore++;
			reader.close();

        }
        if(maxScore == 0) output = "No test cases provided\n";
		else output += ("\nTotal Score: " + score + "/" + maxScore + "\n");


        return output;
    }

    public Submission saveSubmission(String srn, Long assignmentId, MultipartFile file){
        
        try {
            InputStream iStream = file.getInputStream();
			//System.out.println(String.format("Name:%s\nSRN:%s\n", name, srn));


			Path tmp = Files.createTempFile(srn, ".py");
			Files.copy(iStream, tmp, StandardCopyOption.REPLACE_EXISTING);
			iStream.close();
			System.out.println("Saved file to " + tmp);

            String url = environment.getProperty("service_url.assignment") ;
            AssignmentTemplate assignment = restTemplate.getForObject(url+ "/id/" + assignmentId, AssignmentTemplate.class);
            System.out.println(assignment.getTestCases());
            String output = getScore(tmp, assignment.getTestCases());

			Submission s = new Submission();
            //s.setName(name);
            s.setOutput(output);
            s.setSrn(srn);
            s.setAssignmentId(assignmentId);
			s = submissionRepository.save(s);

            restTemplate.postForObject(url + "/submit/" + assignmentId
            + "/" + s.getId() + "/" + srn , 1, Long.class);
            return s;
		}
		catch (Exception e) {
			e.printStackTrace();
            return null;
		}
    }

    public ResponseEntity<String> uploadSubmission(UploadTemplate upload) {

        
        MultipartFile file = upload.getFile();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path tempFilePath ;
        try {
           tempFilePath = Path.of(FileUtils.getTempFilePath(upload.getStudentId(), filename)); 
        } 
        catch (IOException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload file");
        }

        try {
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/files/downloads/")
        .path(tempFilePath.toString())
        .toUriString();
    
        return ResponseEntity.ok(fileDownloadUri);
    }

    public List<Submission> findAllById() {
       return submissionRepository.findAll();
    }
    
}