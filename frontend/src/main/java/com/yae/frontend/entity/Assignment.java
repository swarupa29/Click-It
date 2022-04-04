package com.yae.frontend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Assignment {
    @Id
    @GeneratedValue
    public long id;
    public String title;
    public String description;
    public String deadline;
    public String classAssigned;
    public String subject;
}
