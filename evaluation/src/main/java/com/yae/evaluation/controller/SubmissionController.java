package com.yae.evaluation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.service.SubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;
 
    private RestTemplate restTemplate = new RestTemplate();

    @ResponseBody
    @GetMapping(value="/{id}")
    Submission getSubmission(@PathVariable Long id) {
        return submissionService.findSubmissionById(id);
    }

    @ResponseBody
    @PostMapping(value="/submit" /*,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}*/)
    Submission saveSubmission(
        @RequestParam("assignmentId") Long aId,
        @RequestParam("srn") String srn,
        @RequestParam("file") MultipartFile file)
    {    

        Submission submission = submissionService.saveSubmission(srn, aId, file);

        // Request for assignment microservice
        String url = String.format("http://localhost:7000/submit/%d/%d/%s", submission.assignmentId, submission.id, submission.srn);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.setAccept(Collections.singletonList(MediaType.ALL));

        Map<String, Object> map = new HashMap<>();
        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<Integer> response = this.restTemplate.postForEntity(url, entity, Integer.class);

        System.out.println(response);

        return submission;

        
    } 

    @ResponseBody
    @GetMapping(value="/view")
    List<Submission> getAllSubmissions(){

       return submissionService.findAllById();
    }
}

// MULTIPART FORM IS ALREADY A FORM AND CAN HAVE MULTIPLE FIELDS!