package com.yae.classroom.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Classroom {
    
    @Id
    @GeneratedValue()
    Long classId;

    @ElementCollection
    List<String> students;
    
    @ElementCollection
    List<Long> assignments;
    Long teacher;
    Long ta;
}
