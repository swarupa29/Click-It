package com.yae.frontend.entity;

import java.util.Set;

import javax.persistence.ElementCollection;
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

    @ElementCollection
    Set<Long> classIds;
    // Set<String> classNames;
    // Set<Long> pendingAssignmentIds;
    // Set<Long> completedAssignmentIds;
}
