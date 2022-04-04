package com.yae.assignment.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import com.yae.assignment.RESTTemplates.AssignmentTemplate;
import com.yae.assignment.entity.Assignment;
import com.yae.assignment.repository.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment findAssignmentById(long id) {
        return assignmentRepository.findAssignmentById(id);
    }

    public Assignment saveAssignment(AssignmentTemplate s) throws ParseException {
        
        SimpleDateFormat f = new SimpleDateFormat("dd-mm-yyyy");

        String assignmentTitle = s.getAssignmentTitle();
        String assignmentDescription = s.getAssignmentDescription();
        Date assignedDate = new Date();
        Date deadlineDate = f.parse(s.getDeadlineDate());
        long teacher = s.getTeacher();
        long classAssigned = s.getClassAssigned();
        Map<String, Long> submissions = new HashMap<String, Long>();

        Assignment assignment = new Assignment();
        assignment.setAssignmentTitle(assignmentTitle);
        assignment.setAssignmentDescription(assignmentDescription);
        assignment.setAssignedDate(assignedDate);
        assignment.setDeadlineDate(deadlineDate);
        assignment.setTeacher(teacher);
        assignment.setClassAssigned(classAssigned);
        assignment.setSubmissions(submissions);
        
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(long id, AssignmentTemplate s) throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("dd-mm-yyyy");
        
        String assignmentTitle = s.getAssignmentTitle();
        String assignmentDescription = s.getAssignmentDescription();
        Date deadlineDate = f.parse(s.getDeadlineDate());
        long teacher = s.getTeacher();
        long classAssigned = s.getClassAssigned();

        Assignment assignment = assignmentRepository.findAssignmentById(id);
        assignment.setAssignmentTitle(assignmentTitle);
        assignment.setAssignmentDescription(assignmentDescription);
        assignment.setDeadlineDate(deadlineDate);
        assignment.setTeacher(teacher);
        assignment.setClassAssigned(classAssigned);
    
        return assignmentRepository.save(assignment);
    }

    public long deleteAssignment(Long id) {
        return assignmentRepository.deleteAssignmentById(id);
    }

    public List<Assignment> findAllAssignmentByClass(Long id) {
        return assignmentRepository.findAllByClassAssigned(id);
    }

    public long addSubmission(long assignmentId, long submissionId, String studentId) {
        Assignment a = assignmentRepository.findAssignmentById(assignmentId);
        a.getSubmissions().put(studentId, submissionId);
        return submissionId;
    }
    
}
