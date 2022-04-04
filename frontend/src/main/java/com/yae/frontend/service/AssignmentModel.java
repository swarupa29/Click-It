package com.yae.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.yae.frontend.entity.Assignment;
import com.yae.frontend.repository.AssignmentRepository;
import com.yae.frontend.templates.AssignmentTemplate;

@Service
public class AssignmentModel {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(AssignmentTemplate a) {
        String title = a.getTitle();
        String description = a.getDescription();
        String deadline = a.getDeadline();
        String classAssigned = a.getClassAssigned();
        String subject=a.getSubject();

        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDeadline(deadline);
        assignment.setClassAssigned(classAssigned);
        assignment.setSubject(subject);

        return assignmentRepository.save(assignment);
    }

}
