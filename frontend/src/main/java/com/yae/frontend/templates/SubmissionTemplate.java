package com.yae.frontend.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmissionTemplate {
    public AssignmentTemplate assignment;
    public String submissionFiles;
    public boolean plagarismReport;
    public double marks;
    
}
