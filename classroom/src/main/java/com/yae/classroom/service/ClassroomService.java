package com.yae.classroom.service;

import java.util.*;

import com.yae.classroom.entity.Classroom;
import com.yae.classroom.repository.ClassroomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom save(Classroom s) {
        return classroomRepository.save(s);
    }

    public Classroom findClassById(Long id) {
        return classroomRepository.findClassRoomById(id);
    }
    
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    public Long addStudent(long classroom_id, long student_id) {
        Classroom c = classroomRepository.findClassRoomById(classroom_id);
        c.getStudents().add(student_id);
        classroomRepository.save(c);
        return student_id;
    }

    public Long addAssignment(long classroom_id, long assignment_id) {
        Classroom c = classroomRepository.findClassRoomById(classroom_id);
        c.getAssignments().add(assignment_id);
        classroomRepository.save(c);
        return assignment_id;
    }
}
