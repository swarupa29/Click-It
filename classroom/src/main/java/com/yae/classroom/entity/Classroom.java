package com.yae.classroom.entity;

import java.util.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Classroom {
    @Id
    @GeneratedValue
    Long id;
    String name;

    @ElementCollection
    List<Long> students;

    @ElementCollection
    List<Long> assignments;

    Long teacherId;
    Long taId;
}
