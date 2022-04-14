package com.yae.frontend.templates;

import java.util.HashSet;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    public String id;
    public String name;
    public String password;
    public String email;
    public HashSet<Long> classroomIds;
}
