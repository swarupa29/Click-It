package com.yae.frontend.templates;
import java.util.ArrayList;
import java.util.List;

import com.yae.frontend.entity.Assignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssignmentList {
    private List<Assignment> a;

    public AssignmentList() {
        a = new ArrayList<>();
    }

    public List<Assignment> getAssignments() {

        return a;
    }

    
}
