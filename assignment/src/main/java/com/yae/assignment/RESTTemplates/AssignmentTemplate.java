package com.yae.assignment.RESTTemplates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentTemplate {
    String assignmentTitle;
    String assignmentDescription;
    String deadlineDate;
    long teacher;
    long classAssigned;
}
