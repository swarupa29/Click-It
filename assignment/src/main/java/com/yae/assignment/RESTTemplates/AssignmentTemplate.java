package com.yae.assignment.RESTTemplates;

import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentTemplate {
    long id;
    String assignmentTitle;
    String assignmentDescription;
    Date assignedDate;
    Date deadlineDate;
    long teacher;
    long classAssigned;
    @ElementCollection
    Map<String, Long> submissions;
    @ElementCollection
    Map<String, String> testCases;
}
