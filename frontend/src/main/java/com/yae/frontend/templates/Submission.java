package com.yae.frontend.templates;



import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Submission {
    @Id
    public Long id;
    public String name;
    public String srn;
    public String output;
    public Long assignmentId;
    
}
