package com.yae.evaluation.repository;

import java.util.List;

import com.yae.evaluation.entity.Submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Submission findSubmissionById(Long id);
    List<Submission> findAll();
}
