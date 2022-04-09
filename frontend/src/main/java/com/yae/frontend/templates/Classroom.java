package com.yae.frontend.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Classroom {
    Long id;
    String name;

    List<Long> students;
    List<Long> assignments;

    Long teacherId;
    Long taId;
}