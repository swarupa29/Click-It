package com.yae.evaluation.service;

import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission findSubmissionById(String id) {
        return submissionRepository.findSubmissionById(id);
    }

    public Submission saveSubmission(Submission s) {
        return submissionRepository.save(s);
    }
    
}
