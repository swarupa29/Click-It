package com.yae.evaluation.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.filechooser.FileView;

import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class SubmissionService {
    private String USER= System.getProperty("user.name");
    private final String  FILE_BASE_PATH="/home/"+USER+"/yae";

    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission findSubmissionById(String id) {
        return submissionRepository.findSubmissionById(id);
    }

    public Submission saveSubmission(Submission s) {
        return submissionRepository.save(s);
    }

    public ResponseEntity uploadSubmission(MultipartFile file) {

        

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        // path where uploaded file will be stored
        Path path = Paths.get(FILE_BASE_PATH, filename);
        File f = new File(FILE_BASE_PATH);
        f.mkdirs();

        // copies file to the path, replacing if same named file exists. TODO: Versioning  
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // returns a uri for the user to download the file they uploaded (for confirmation)
        // NOTE: the path needs to have a controller which enables users to download file.
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/files/downloads/")
            .path(filename)
            .toUriString();
        
        return ResponseEntity.ok(fileDownloadUri);
    }
    
}
