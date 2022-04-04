package com.yae.evaluation.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.yae.evaluation.RESTTemplates.UploadTemplate;
import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.repository.SubmissionRepository;
import com.yae.evaluation.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
     
    public Submission findSubmissionById(Long id) {
        return submissionRepository.findSubmissionById(id);
    }

    public Submission saveSubmission(String name, String srn, Long assignmentId, MultipartFile file){
        
        try {
            InputStream iStream = file.getInputStream();
			System.out.println(String.format("Name:%s\nSRN:%s\n", name, srn));


			Path tmp = Files.createTempFile(srn, ".py");
			Files.copy(iStream, tmp, StandardCopyOption.REPLACE_EXISTING);
			iStream.close();
			System.out.println("Saved file to " + tmp);

			String command = String.format("python %s", tmp);
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String output = new String("");

			while ((line = reader.readLine()) != null) {
				output += line+"\n";
			}
			reader.close();
			
			Submission s = new Submission();
            s.setName(name);
            s.setOutput(output);
            s.setSrn(srn);
            s.setAssignmentId(assignmentId);
			return submissionRepository.save(s);
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