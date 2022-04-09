package com.yae.student.controller;

import java.math.BigInteger;
import java.util.LinkedHashMap;

import com.yae.student.RESTTemplates.StudentTemplate;
import com.yae.student.entity.Student;
import com.yae.student.service.StudentService;

import org.apache.tomcat.util.json.JSONParser;
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
public class StudentController {
    
    @Autowired
    StudentService studentService;

    @GetMapping("{id}")
    Student getStudent(@PathVariable String id) {
        return studentService.findStudentById(id);
    }

    @PostMapping("save")
    Student postMethodName(@RequestBody StudentTemplate s) {
        return studentService.saveStudent(s);
    }
    
    @PostMapping("join")
    ResponseEntity<String> joinClass(@RequestBody String body) throws org.apache.tomcat.util.json.ParseException {
        LinkedHashMap<String, Object> req  = new JSONParser(body).object();
        studentService.addStudentToClassroom((String) req.get("userId"), ((BigInteger) req.get("classId")).longValue());
        
        return new ResponseEntity<>("Succesfully joined class", HttpStatus.OK);
    }

    @PostMapping("leave")
    ResponseEntity<String> leaveClass(@RequestBody String body) throws org.apache.tomcat.util.json.ParseException {
        LinkedHashMap<String, Object> req  = new JSONParser(body).object();
        studentService.removeStudentFromClass((String) req.get("userId"), ((BigInteger) req.get("classId")).longValue());
        
        return new ResponseEntity<>("Succesfully joined class", HttpStatus.OK);
    }
}
