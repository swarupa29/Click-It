package com.yae.student.controller;

import com.yae.student.RESTTemplates.StudentTemplate;
import com.yae.student.entity.Student;
import com.yae.student.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    
}
