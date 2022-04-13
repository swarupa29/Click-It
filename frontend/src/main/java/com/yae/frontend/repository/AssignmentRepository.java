package com.yae.frontend.repository;

import java.util.Map;

import com.yae.frontend.entity.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Assignment findAssignemntById(Long id);
    Map<String, Long> getSubmissions();
}
