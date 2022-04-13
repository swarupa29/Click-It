package com.yae.frontend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.yae.frontend.entity.Assignment;
import com.yae.frontend.repository.AssignmentRepository;

@Service
public class AssignmentModel {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment a) {
        String title = a.getAssignmentTitle();
        String description = a.getAssignmentDescription();
        Date deadline = a.getDeadlineDate();
        //Long classAssigned = a.getClassAssigned();

        Assignment assignment = new Assignment();
        assignment.setAssignmentTitle(title);
        assignment.setAssignmentDescription(description);
        assignment.setDeadlineDate(deadline);
        //assignment.setClassAssigned(classAssigned);

        return assignmentRepository.save(assignment);
    }

}
