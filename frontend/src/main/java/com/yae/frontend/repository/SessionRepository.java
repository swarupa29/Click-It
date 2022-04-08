package com.yae.frontend.repository;

import java.util.List;

import com.yae.frontend.entity.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    public Session findSessionById(Long id);
    public List<Session> findAll();
    public void deleteById(Long id);
}
