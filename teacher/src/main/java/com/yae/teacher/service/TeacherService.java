package com.yae.teacher.service;

import org.springframework.stereotype.Service;

import com.yae.teacher.entity.Teacher;
import com.yae.teacher.repository.TeacherRepository;
import com.yae.teacher.template.Classroom;
import com.yae.teacher.template.TeacherTemplate;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher findTeacherById(String id) {
        return teacherRepository.findTeacherById(id);
    }


    public Teacher saveTeacher(Teacher t) {
        String id = t.getId();
        String name = t.getName();
        String password = t.getPassword();
        String email = t.getEmail();
        HashSet<Long> classIds = new HashSet<>();


        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(id);
        savedTeacher.setName(name);
        savedTeacher.setPassword(password);
        savedTeacher.setEmail(email);
        savedTeacher.setClassroomIds(classIds);

        return teacherRepository.save(savedTeacher);
    }

    public String updateTeacher(String id, Classroom c) 
    {
        Teacher teacher= findTeacherById(id);
        System.out.println("inside teacher ms: id passed is");
        System.out.println(c.getId());
        teacher.ClassroomIds.add(c.getId());
        //Teacher t2=teacherService.saveTeacher(teacher);
        Teacher t2=teacherRepository.save(teacher);
        System.out.println("insede teacher after saving");
        System.out.println(t2.getClassroomIds());
        return "Success";
    }
    
}
