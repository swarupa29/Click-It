package com.yae.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yae.student.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentById(String id);
}
