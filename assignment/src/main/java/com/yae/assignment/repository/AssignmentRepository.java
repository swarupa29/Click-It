package com.yae.assignment.repository;

import java.util.List;

import com.yae.assignment.entity.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Assignment findAssignmentById(Long id);
    long deleteAssignmentById(Long id);
    List<Assignment> findAllAssignmentByClass(Long id);
}
