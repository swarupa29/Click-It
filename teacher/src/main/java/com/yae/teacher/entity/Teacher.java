package com.yae.teacher.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ElementCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {
    @Id
    public String id;
    public String name;
    public String password;
    public String email;
    
    @ElementCollection
    public Set<Long> ClassroomIds;
}
