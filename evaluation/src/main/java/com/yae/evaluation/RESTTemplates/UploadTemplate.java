package com.yae.evaluation.RESTTemplates;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadTemplate {
    Long studentId;
    Long assignmentId;
    MultipartFile file;
}
