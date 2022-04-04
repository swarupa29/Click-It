package com.yae.frontend.templates;

import java.io.File;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmissionTemplate {
    public File testcase;
    public boolean plagarismReport;
    public double marks;
    
}
