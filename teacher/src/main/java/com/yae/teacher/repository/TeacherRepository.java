package com.yae.teacher.repository;

import com.yae.teacher.entity.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{
    Teacher findTeacherById(String id);
    
}
