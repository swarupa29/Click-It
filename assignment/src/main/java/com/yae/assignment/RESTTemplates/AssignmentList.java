package com.yae.assignment.RESTTemplates;
import java.util.ArrayList;
import java.util.List;

import com.yae.assignment.entity.Assignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AssignmentList {
    

    List<Assignment> alist;
    //AssignmentList(List<Assignment> a2)
    {

    }

    public List<Assignment> getAssignments() {

        return alist;
    }

    
}
