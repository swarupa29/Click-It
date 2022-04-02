package com.yae.frontend.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentTemplate {
    public String title;
    public String description;
    public String deadline;
    public String classAssigned;
    public String subject;
    public boolean submitted;
    
}
