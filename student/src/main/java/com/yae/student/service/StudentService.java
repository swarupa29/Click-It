package com.yae.student.service;

import com.yae.student.RESTTemplates.StudentTemplate;
import com.yae.student.entity.Student;
import com.yae.student.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student findStudentById(String id) {
        return studentRepository.findStudentById(id);
    }

    public Student saveStudent(StudentTemplate s) {
        String id = s.getId();
        String name = s.getName();
        int age = s.getAge();
        String email = s.getEmail();

        Student savedStudent = new Student();
        savedStudent.setId(id);
        savedStudent.setName(name);
        savedStudent.setAge(age);
        savedStudent.setEmail(email);
        
        return studentRepository.save(savedStudent);
    }
}
