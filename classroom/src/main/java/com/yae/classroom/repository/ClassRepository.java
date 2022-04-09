package com.yae.classroom.repository;

import java.util.List;

import com.yae.classroom.RestTemplates.Assignment;
import com.yae.classroom.RestTemplates.Student;
import com.yae.classroom.entity.Classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classroom, Long>{
    Classroom findClassroomById(Long id);
    List<Classroom> findAll();
    List<Student> findStudentsByClassroomId(Long id);
    List<Assignment> findAssignmentsByClassroomId(Long id);
    List<Classroom> findByStudent(Long studentId);
    void deleteById();
    void updateById();
    
}
