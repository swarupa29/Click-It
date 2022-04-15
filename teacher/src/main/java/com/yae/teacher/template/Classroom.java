package com.yae.teacher.template;

import java.util.List;

import javax.persistence.ElementCollection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Classroom {
    Long id;
    String name;

    @ElementCollection
    List<Long> students;

    @ElementCollection
    List<Long> assignments;

    String teacherId;
    Long taId;
    
}
