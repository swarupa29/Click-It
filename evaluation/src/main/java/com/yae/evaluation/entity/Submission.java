package com.yae.evaluation.entity;

import java.util.Date;

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
public class Submission {
    @Id
    @GeneratedValue()
    public Long id;
    public String studentId;
    public String fileName;
    public int finalScore;
    public Date submittedOn;    
    public String assignmnentId;
}
