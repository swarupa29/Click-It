package com.yae.assignment.controller;

import java.text.ParseException;
import java.util.List;

import com.yae.assignment.RESTTemplates.AssignmentTemplate;
import com.yae.assignment.entity.Assignment;
import com.yae.assignment.service.AssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AssignmentController {
    
    @Autowired
    AssignmentService assignmentService;

    @GetMapping("id/{id}")
    Assignment getAssignment(@PathVariable Long id) {
        return assignmentService.findAssignmentById(id);
    }

    @PostMapping("save")
    Assignment saveAssignment(@RequestBody AssignmentTemplate s) throws ParseException {
        return assignmentService.saveAssignment(s);
    }

    @PostMapping("update/{id}")
    Assignment updateAssignment(@PathVariable long id, @RequestBody AssignmentTemplate s) throws ParseException {
        return assignmentService.updateAssignment(id, s);
    }

    @PostMapping("delete/{id}")
    long deleteAssignment(@PathVariable long id) {
        return assignmentService.deleteAssignment(id);
    }

    @GetMapping("class/{classId}")
    List<Assignment> findAllByClass(@PathVariable long classId) {
        return assignmentService.findAllByClass(classId);
    }

    @PostMapping("submit/{assignmentId}/{submissionId}")
    long submit (@PathVariable long assignmentId, @PathVariable long submissionId) {
        return assignmentService.addSubmission(assignmentId, submissionId);
    }
}
