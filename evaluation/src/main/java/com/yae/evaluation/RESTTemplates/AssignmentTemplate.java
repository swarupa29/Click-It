package com.yae.evaluation.RESTTemplates;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Getter;

@Getter
public class AssignmentTemplate implements Serializable {
    long id;
    String assignmentTitle;
    String assignmentDescription;
    Date assignedDate;
    Date deadlineDate;
    long teacher;
    long classAssigned;
    Map<String, Long> submissions;
    Map<String, String> testCases;
}
