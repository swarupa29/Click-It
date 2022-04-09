package com.yae.frontend.entity;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Session {
    @Id
    Long id;
    String userId;
    String name;
    String email;
    HashSet<Long> classIds;
    // List<String> classNames;
    // List<Long> pendingAssignmentIds;
    // List<Long> completedAssignmentIds;
}
