package com.yae.classroom.repository;

import com.yae.classroom.entity.Classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>  {
    Classroom findClassRoomById(Long id);
}
