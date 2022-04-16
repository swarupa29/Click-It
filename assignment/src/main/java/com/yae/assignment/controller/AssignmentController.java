package com.yae.assignment.controller;

import java.text.ParseException;
import java.util.List;

import com.yae.assignment.RESTTemplates.AssignmentList;
import com.yae.assignment.RESTTemplates.AssignmentTemplate;
import com.yae.assignment.entity.Assignment;
import com.yae.assignment.service.AssignmentService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/save")
    Assignment saveAssignment(@RequestBody AssignmentTemplate s) {
        System.out.println("in save");
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
    AssignmentList findAllAssignmentByClass(@PathVariable long classId) {

        List<Assignment>a2= assignmentService.findAllAssignmentByClass(classId);
        AssignmentList res= new AssignmentList(a2);
        return res;
    }

     @PostMapping("submit/{assignmentId}/{submissionId}/{studentId}")
    long submit (@PathVariable long assignmentId, @PathVariable long submissionId, @PathVariable String studentId) {
        return assignmentService.addSubmission(assignmentId, submissionId, studentId);
    }

    @PostMapping("add-test-case/")
    ResponseEntity<String> addTestCase( @RequestBody String reqBody) throws org.json.simple.parser.ParseException {
        JSONObject body = (JSONObject) new JSONParser().parse(reqBody);
        Long assignmentId = (Long) body.get("assignmentId");
        String input = (String) body.get("input");
        String output = (String) body.get("output");

        if (assignmentService.addTestCase(assignmentId, input, output))
            return new ResponseEntity<String>("Added new test case!", HttpStatus.OK);
        else 
            return new ResponseEntity<String>("Failed to add test case", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
