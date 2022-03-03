package com.yae.evaluation.controller;

import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.service.SubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @GetMapping("{id}")
    Submission getSubmission(@PathVariable String id) {
        return submissionService.findSubmissionById(id);
    }

    @PostMapping("save")
    Submission saveSubmission(@RequestBody Submission s){
        return submissionService.saveSubmission(s);
    } 
}
