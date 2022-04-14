package com.yae.teacher.service;

import org.springframework.stereotype.Service;

import com.yae.teacher.entity.Teacher;
import com.yae.teacher.repository.TeacherRepository;
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


    public Teacher saveTeacher(TeacherTemplate t) {
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
    
}
