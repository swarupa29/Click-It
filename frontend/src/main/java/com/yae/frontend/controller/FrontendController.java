package com.yae.frontend.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.service.FrontendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FrontendController {

   
    @Autowired
    private FrontendService service;

    // HOME PAGE
    @GetMapping({"/student","/"})
    public String landing(HttpServletRequest request, HttpServletResponse response, Model model,Long cid){
        return service.landing(request, response, model);
    }

    @GetMapping("/teacher")
    public String landingTeacher(HttpServletRequest request, HttpServletResponse response, Model model){
        return service.landingTeacher(request, response, model);
    }
    
    // SUBMIT LOGIN FORM 
    @PostMapping("/login")
    public String login(@RequestParam("srn") String srn,@RequestParam("usertype") String usertype, HttpServletResponse response)
    throws IOException {
        System.out.println(usertype);
        
        return service.login(srn,usertype, response);
    }

    //REDIRECT TO SIGNUP PAGE
    @GetMapping(value="/signup")
    public String signupPage()
    {
        return "signup";
    }

    //REGISTER STUDENT
    @PostMapping("/signup")
    public String signupStudent(@RequestParam("name") String name, @RequestParam("email") String email,@RequestParam("srn") String srn,@RequestParam("password") String password,@RequestParam("usertype") String usertype)
    {
        return service.signup(name,email,srn,password,usertype);
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


    @PostMapping(value="/expandAssignment/{aid}/{submitted}")
    public String expandAssignmentPost(@PathVariable("aid") String aid,@PathVariable("submitted") String submitted,Model model,HttpServletRequest request)
    {

        return service.expandAssignment(aid,submitted,model,request);
       
    }

    @GetMapping(value="/expandAssignment/{aid}/{submitted}")
    public String expandAssignmentGet(@PathVariable("aid") String aid,@PathVariable("submitted") String submitted,Model model,HttpServletRequest request)
    {

        return service.expandAssignment(aid,submitted,model,request);
       
    }

    @PostMapping(value="/submit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void Submission(
        @RequestParam("file") MultipartFile file,
        Model model,HttpServletRequest request,HttpServletResponse response) throws IOException
    {    

       service.submitAssignment(file,model,request,response);
        
    } 

    @GetMapping(value="/changeClass")
    public String changeClass(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("id") Long id) throws IOException{ 
        return service.changeClass(request,response,id);
    }

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
    public void createClass(@CookieValue String userId,@RequestParam("classId") String className,HttpServletResponse response) throws IOException
    {
        //call save using class service 
        System.out.println(className);
        service.createClass(userId,className,response);
    }


    @PostMapping("/saveAssignment")
    public void addAssignment(@RequestParam Map<String,String> body,HttpServletResponse response) throws ParseException,IOException
    {
        //return service.saveAssignment();IOUtils.toString
        System.out.println("save controller");
        service.addAssignment(body,response);
    }

    @GetMapping("/expandAssignmentTeacher")
    public String expandAssignmentTeacher(@RequestParam("id") Long id,Model model)
    {
        return service.expandAssignmentTeacher(id,model);
    }

    @GetMapping("/goback")
    public void goback(HttpServletResponse response) throws IOException
    {
        //response.sendRedirect(environment.getProperty("service_url.frontend")+"/login");
        service.goback(response);
    }

    
}



