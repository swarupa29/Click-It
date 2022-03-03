package com.yae.evaluation.repository;

import com.yae.evaluation.entity.Submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
    Submission findSubmissionById(String id);
}
