package com.yae.evaluation.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import com.yae.evaluation.RESTTemplates.SubmissionTemplate;
import com.yae.evaluation.RESTTemplates.UploadTemplate;
import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.repository.SubmissionRepository;
import com.yae.evaluation.utils.*;

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

    public Submission findSubmissionById(String id) {
        return submissionRepository.findSubmissionById(id);
    }

    public Submission saveSubmission(SubmissionTemplate s){
        
        String assignmnentId = s.getAssignmnentId();
        String studentId = s.getStudentId();
        String fileName = s.getFileName();
        Date submittedOn = new Date();

        try {
            Path filePath = Path.of(FileUtils.getSubmissionFilePath(studentId, s.getAssignmnentId(), fileName));          
            Path tempPath = Path.of(FileUtils.getTempFilePath(studentId, fileName));
            Files.createDirectories(filePath.getParent());
            Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        Submission submission = new Submission();
        submission.setAssignmnentId(assignmnentId);
        submission.setFileName(fileName);
        submission.setStudentId(studentId);
        submission.setSubmittedOn(submittedOn);
        return submissionRepository.save(submission);
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
    
}