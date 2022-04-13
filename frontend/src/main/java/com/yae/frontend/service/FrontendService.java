package com.yae.frontend.service;

import java.io.IOException;
import java.util.ArrayList;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.entity.Assignment;
import com.yae.frontend.entity.Session;
import com.yae.frontend.model.AssignmentModel;
import com.yae.frontend.repository.SessionRepository;
import com.yae.frontend.templates.AssignmentList;
import com.yae.frontend.templates.AssignmentTemplate;
import com.yae.frontend.templates.Classroom;
import com.yae.frontend.templates.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

@Service
public class FrontendService {

    @Autowired 
    private SessionRepository sessionRepository;
    
    @Autowired
    private Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Integer> postForObject(MultipartFile file) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);



        MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<MultiValueMap<String,Object>>(map,headers);


        String url="https://localhost:5000";
        System.out.println("here");
        return restTemplate.postForEntity(url, entity, Integer.class);
    }
     
      
    public String login(String srn, HttpServletResponse response) throws IOException{

        // TODO Fetch Student Object, Validate if exists and then store in session storage.

        Long sessionId = Math.round(Math.random()*100);
        Cookie cookie_1 = new Cookie("userId", srn);
        Cookie cookie_2 = new Cookie("sessionId", Long.toString(sessionId));
        cookie_1.setPath("/");
        cookie_2.setPath("/");
        response.addCookie(cookie_1);
        response.addCookie(cookie_2);

        System.out.println(environment.getProperty("service_url.student")+"/"+srn);
        Student student = restTemplate.getForObject(environment.getProperty("service_url.student")+"/"+srn, Student.class);
        if(student == null) {
            response.sendRedirect(environment.getProperty("service_url.frontend")+"/error");
            return "login";
        }
        
        Session session = new Session();
        session.setId(sessionId);
        session.setUserId(srn);
        session.setClassIds(student.classroomIds);
        System.out.println("Classroom Ids:" + student.classroomIds);

        sessionRepository.save(session);

        response.sendRedirect(environment.getProperty("service_url.frontend")+"/");
 
        return "login";
        
    }

    
    public String landing(HttpServletRequest request, HttpServletResponse response, Model model){

        Cookie [] cookies = request.getCookies();

        if(cookies == null)                 
        { 
            try {
                response.sendRedirect(environment.getProperty("service_url.frontend")+"/login");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        else{
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    Session session = sessionRepository.findSessionById(Long.parseLong(sessionId));
                    
                   

                    if (session != null) {

                        Student student = restTemplate.getForObject(environment.getProperty("service_url.student")+"/"+session.getUserId(), Student.class);

                        // NOTE: Update any session data here
                        session.setClassIds(student.classroomIds);
                        sessionRepository.save(session);
                        // Fetch List of Classrooms
                        Set<Long> classIds = session.getClassIds();
                        System.out.println(classIds);
                        // classIds.add((long)1);
                        Set<Classroom> classes =  new HashSet<>();

                        classes=getclasses(classIds);


                        Classroom class1= classes.iterator().next();                        
                        List<Assignment> assignment=getAssignments(class1.getId());
                        //TBD 
                        List<List<Assignment>> classified= classifyAssignments(assignment,session);
                        List<Assignment> pending= classified.get(0);
                        List<Assignment> submitted= classified.get(1);
                        model.addAttribute("pending",pending);
                        model.addAttribute("submitted",submitted);                    
                        model.addAttribute("classes", classes);
                        return "index";
                    }
                }
            }
            try {
                response.sendRedirect(environment.getProperty("service_url.frontend")+"/login");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        
        return "index";
    }

    public List<List<Assignment>> classifyAssignments(List<Assignment> a,Session session)
    {
        List<List<Assignment>> final_list = new ArrayList<>();
        List<Assignment> submitted=new ArrayList<>();;
        List<Assignment> pending=new ArrayList<>();
        ;
        for(int i=0;i<a.size();i++)
        {
            if(a.get(i).getSubmissions().containsKey(session.getUserId()))
            {
                submitted.add(a.get(i));
            }
            else pending.add(a.get(i));


        }
        final_list.add(pending);

        final_list.add(submitted);

        return final_list;
    }
    public List<Assignment> getAssignments(Long Id)
    {
        AssignmentList alist= restTemplate.getForObject(environment.getProperty("service_url.assignment")+"/class/"+Id,AssignmentList.class);
        List<Assignment> a= alist.getAssignments();
        return a;
    }


    public Set<Classroom> getclasses(Set<Long> classIds)
    {
        Set<Classroom> classes =  new HashSet<>();
        for(long id: classIds) {
            Classroom classroom = restTemplate.getForObject(environment.getProperty("service_url.classroom")+"/"+id, Classroom.class);
            classes.add(classroom);
            System.out.println(classroom.getName());
        }

        return classes;

    }

    public ResponseEntity<String> joinClass(String userId, Long classId, HttpServletResponse response) throws IOException {

        JSONObject classJoinReq = new JSONObject();
        classJoinReq.put("userId", userId);
        classJoinReq.put("classId", classId);
        
        HttpEntity<String> request = new HttpEntity<>(classJoinReq.toJSONString());

        response.sendRedirect(environment.getProperty("service_url.frontend"));
        return restTemplate.postForEntity(environment.getProperty("service_url.student")+"/join", request, String.class);


    }


    public String expandAssignment(String title, String description, String deadline,Model model,Assignment a, AssignmentModel assignmentModel)
    {
        //get assignment files from assignment Service

        assignmentModel.saveAssignment(a);
        model.addAttribute("result","");
        model.addAttribute("marks","NA");

        return "assignment";
    }

    public String submitAssignment(MultipartFile file, Model model, AssignmentModel assignmentModel)
    {
        //make call to the ms to submit the file
        //String output = submissionService.saveSubmission(file);

        //Submission s=new Submission(file);
        ResponseEntity<Integer> mark;
        mark = postForObject(file);
        /*
        String output="Code files have been submitted succesfully";
        java.util.List<Assignment> a = assignmentModel.getAllAssignments();
        model.addAttribute("title",a.get(0).getTitle());
        model.addAttribute("deadline",a.get(0).getDeadline());
        model.addAttribute("description",a.get(0).getDescription());
        model.addAttribute("result", output);
        System.out.println("HERE!");

        //mark=get from evalv service
        //int mark=15;

        model.addAttribute("marks",mark.getBody());

        */
        return "assignment";
    }
}