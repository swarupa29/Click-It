package com.yae.teacher.controller;

import com.yae.teacher.entity.Teacher;
import com.yae.teacher.service.TeacherService;
import com.yae.teacher.template.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TeacherController {


    @Autowired
    TeacherService teacherService;

    @GetMapping("{id}")
    Teacher getTeacher(@PathVariable String id) {
        return teacherService.findTeacherById(id);
    }

    @PostMapping("save")
    Teacher postMethodName(@RequestBody Teacher s) {
        System.out.println("saving");
        return teacherService.saveTeacher(s);
    }

    @PostMapping("addClass/{id}")
    String addClass(@PathVariable String id,@RequestBody Classroom c) 
    {

        return teacherService.updateTeacher(id,c);
                
    }
    
}
