package com.yae.classroom.controller;

import java.util.ArrayList;
import java.util.List;

import com.yae.classroom.RESTTemplates.ClassroomTemplate;
import com.yae.classroom.entity.Classroom;
import com.yae.classroom.service.ClassroomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ClassroomController {
    @Autowired
    ClassroomService classroomService;

    @GetMapping("{id}")
    Classroom getClassroom(@PathVariable long id) {
        return classroomService.findClassById(id);
    }

    @GetMapping("all")
    List<Classroom> findAll() {
        return classroomService.findAll();
    }

    @PostMapping("save")
    Classroom save(@RequestBody ClassroomTemplate s) {
        String name = s.getName();
        long teacherId = s.getTeacherId();
        long taId = s.getTaId();

        Classroom c = new Classroom();
        c.setName(name);
        c.setTeacherId(teacherId);
        c.setTaId(taId);
        c.setAssignments(new ArrayList<>());
        c.setStudents(new ArrayList<>());
        return classroomService.save(c);
    }

    @PostMapping("addStudent/{classroom_id}/{student_id}")
    Long addStudent(@PathVariable long classroom_id, @PathVariable long student_id) {
        return classroomService.addStudent(classroom_id, student_id);
    }

    @PostMapping("addAssignment/{classroom_id}/{assignment_id}")
    Long addAssignment(@PathVariable long classroom_id, @PathVariable long assignment_id) {
        return classroomService.addAssignment(classroom_id, assignment_id);
    }
}
