package com.yae.frontend.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import javax.persistence.ElementCollection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {
    Long id;
    String name;

    @ElementCollection
    List<Long> students;

    @ElementCollection
    List<Long> assignments;

    Long teacherId;
    Long taId;
}
