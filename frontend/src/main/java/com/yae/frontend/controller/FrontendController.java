package com.yae.frontend.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.service.AssignmentModel;
import com.yae.frontend.service.FrontendService;
import com.yae.frontend.templates.AssignmentTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FrontendController {

    @Autowired
    private AssignmentModel assignmentModel;
   
    @Autowired
    private FrontendService service;

    // HOME PAGE
    @GetMapping("/")
    public String landing(HttpServletRequest request, HttpServletResponse response, Model model){
        return service.landing(request, response, model);
    }
    
    // SUBMIT LOGIN FORM 
    @PostMapping("/login")
    public String login(@RequestParam("srn") String srn, HttpServletResponse response)
    throws IOException {
        return service.login(srn, response);
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    // @GetMapping("/index")
    // public String showList(Model model) {
    //     //String urlstr="http://localhost:8080/GetAllClasses";
    //     String desc="this is description of an assignment that has been made temporary";
        
    //             /*
    //             HttpURLConnection con = (HttpURLConnection) url.openConnection();
    //             con.setRequestMethod("GET");
    //             BufferedReader in = new BufferedReader(
    //             new InputStreamReader(con.getInputStream()));
    //             String inputLine;

    //             StringBuffer response = new StringBuffer();

    //             while ((inputLine = in.readLine()) != null) {
    //                 response.append(inputLine);
    //             }
    //             in.close();
    //             System.out.println(response.toString());
    //             */

    //     //testing 
    //     ClassTemplate response1=new ClassTemplate("Class a");
    //     ClassTemplate response2=new ClassTemplate("Class b");
    //     List<ClassTemplate> lst= new ArrayList<ClassTemplate>();
    //     lst.add(response1);
    //     lst.add(response2);

    //     AssignmentTemplate res= new AssignmentTemplate("CD",desc,"1/4/22","class a","cd",true);

    //     AssignmentTemplate res2= new AssignmentTemplate("MVC",desc,"22/4/22","class a","ooad",false);
    //     model.addAttribute("assignment1",res2);
    //     model.addAttribute("assignment2",res);

    //     model.addAttribute("classes",lst);

    //     return "index";
    // }

    @GetMapping(value="/expandAssignment")
    public String expandAssignment(@ModelAttribute("title") String title, @ModelAttribute("description") String description,
     @ModelAttribute("deadline") String deadline,Model model, AssignmentTemplate a)
    {

        return service.expandAssignment(title, description,deadline,model,a,assignmentModel);
       
    }


    @PostMapping(value="/submit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String Submission(
        @RequestParam("file") MultipartFile file,
        Model model)
    {    

        return service.submitAssignment(file,model,assignmentModel);
        
    } 

    @GetMapping(value="/changeClass")
    public String changeClass(Model model, @ModelAttribute("name") String name){ return "index";}

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> joinClass(@CookieValue String userId, @RequestParam Long classId, HttpServletResponse response) throws IOException{        
        return service.joinClass(userId, classId, response);
        
    }
    @GetMapping("/teacherHome")
    public String teacherhome(){
        return "teacherHome";
    }

    @GetMapping(value="createAssignment")
    public String createAssignment()
    {
        return "createAssignment";
    }

    @PostMapping("/createClass")
    public String createClass(@RequestParam("classId") String className)
    {
        //call save using class service 
        System.out.println(className);

       // teacherhome();
        return "teacherHome";
    }
    
}



