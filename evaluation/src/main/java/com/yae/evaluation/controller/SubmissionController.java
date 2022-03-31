package com.yae.evaluation.controller;

import com.yae.evaluation.entity.Submission;
import com.yae.evaluation.service.SubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @GetMapping(value="/")
    String home(Model model){
        return "index";
    }
 
    @GetMapping(value="/{id}")
    @ResponseBody
    Submission getSubmission(@PathVariable Long id) {
        return submissionService.findSubmissionById(id);
    }

    @PostMapping(value="/submit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String saveSubmission(
        @RequestParam("name") String name,
        @RequestParam("srn") String srn,
        @RequestParam("file") MultipartFile file,
        Model model)
    {    
        String output = submissionService.saveSubmission(name, srn, file);

        model.addAttribute("result", output);
        model.addAttribute("submissionStatus", "SUCCESS");
        System.out.println("HERE!");
        return "index";
    } 

    @GetMapping(value="/view")
    String getAllSubmissions(Model model){

        model.addAttribute("rows", submissionService.findAllById());
        return "table";
    }
}

// MULTIPART FORM IS ALREADY A FORM AND CAN HAVE MULTIPLE FIELDS!