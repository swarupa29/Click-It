package com.yae.frontend.templates;

import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Assignment {
    @Id
    Long id;
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
