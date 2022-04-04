package com.yae.frontend.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yae.frontend.entity.Assignment;
import com.yae.frontend.service.AssignmentModel;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
*/
import com.yae.frontend.templates.AssignmentTemplate;
import com.yae.frontend.templates.ClassTemplate;
import com.yae.frontend.templates.SubmissionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FrontendController {
    @Autowired
    AssignmentModel assignmentModel;

    @GetMapping("/index")
    public String showList(Model model) {
        //String urlstr="http://localhost:8080/GetAllClasses";
        String desc="this is descri[tion of an assignment that has been made temporary";
        
                /*
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
                String inputLine;

                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                */

        //testing 
        ClassTemplate response1=new ClassTemplate("Class a");
        ClassTemplate response2=new ClassTemplate("Class b");
        List<ClassTemplate> lst= new ArrayList<ClassTemplate>();
        lst.add(response1);
        lst.add(response2);

        AssignmentTemplate res= new AssignmentTemplate("CD",desc,"1/4/22","class a","cd",true);

        AssignmentTemplate res2= new AssignmentTemplate("MVC",desc,"22/4/22","class a","ooad",false);
        model.addAttribute("assignment1",res2);
        model.addAttribute("assignment2",res);

        model.addAttribute("classes",lst);

        return "index";
    }

    @GetMapping(value="expandAssignment")
    public String expandAssignment(@ModelAttribute("title") String title, @ModelAttribute("description") String description,
     @ModelAttribute("deadline") String deadline,Model model, AssignmentTemplate a)
    {
        System.out.println(a.deadline);
        //get assignment files from assignment Service
        File f= new File("sample.txt");
        SubmissionTemplate res2= new SubmissionTemplate(f,false,15);
        assignmentModel.saveAssignment(a);
        model.addAttribute("submission",res2);
        model.addAttribute("result","");
        model.addAttribute("marks","NA");

        System.out.println(res2.testcase);
        return "assignment";
    }


    @PostMapping(value="/submit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String Submission(
        @RequestParam("file") MultipartFile file,
        Model model)
    {    
        //make call to the ms to submit the file
        //String output = submissionService.saveSubmission(file);

        String output="Code files have been submitted succesfully";
        java.util.List<Assignment> a = assignmentModel.getAllAssignments();
        model.addAttribute("title",a.get(0).getTitle());
        model.addAttribute("deadline",a.get(0).getDeadline());
        model.addAttribute("description",a.get(0).getDescription());
        model.addAttribute("result", output);
        File f= new File("sample.txt");
        SubmissionTemplate res2= new SubmissionTemplate(f,false,15);
        model.addAttribute("submission",res2);
        System.out.println("HERE!");

        //mark=get from evalv service
        int mark=15;
        model.addAttribute("marks",mark);


        return "assignment";
    } 

    @GetMapping(value="changeClass")
    public String changeClass(Model model, @ModelAttribute("name") String name)
    {

        //Get assignments for that class from the ms

        return "index";
    }

    
}



