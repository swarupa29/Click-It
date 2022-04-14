package com.yae.student.service;

import java.util.HashSet;

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
        String password = s.getPassword();
        String email = s.getEmail();
        HashSet<Long> classIds = new HashSet<>();

        Student savedStudent = new Student();
        savedStudent.setId(id);
        savedStudent.setName(name);
        savedStudent.setPassword(password);
        savedStudent.setEmail(email);
        savedStudent.setClassroomIds(classIds);

        return studentRepository.save(savedStudent);
    }

    public void addStudentToClassroom(String id, Long classId) {
        Student s = studentRepository.findStudentById(id);
        s.getClassroomIds().add(classId);
        studentRepository.save(s);
    }

    public void removeStudentFromClass(String id, Long classId) {
        Student s = studentRepository.findStudentById(id);
        s.getClassroomIds().remove(classId);
        studentRepository.save(s);
    }

}
