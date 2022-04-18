package com.yae.frontend.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.entity.Session;
import com.yae.frontend.repository.SessionRepository;
import com.yae.frontend.templates.Assignment;
import com.yae.frontend.templates.AssignmentList;
import com.yae.frontend.templates.Classroom;
import com.yae.frontend.templates.Student;
import com.yae.frontend.templates.Submission;
import com.yae.frontend.templates.Teacher;

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

    private int flag=0;

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
     

    public String signup(String name,String email,String srn, String password,String usertype )
    {
        if(usertype=="student")
        {
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setId(srn);

            restTemplate.postForObject(environment.getProperty("service_url.student")+"/save",student,Student.class);
        }
        else
        {
            Teacher teacher=new Teacher();
            teacher.setName(name);
            teacher.setEmail(email);
            teacher.setPassword(password);
            teacher.setId(srn);
            restTemplate.postForObject(environment.getProperty("service_url.teacher")+"/save",teacher,Teacher.class);

        }

        return "login";
    }
      
    public String login(String srn,String usertype, HttpServletResponse response) throws IOException{

        // TODO Fetch Student Object, Validate if exists and then store in session storage.

        Long sessionId = Math.round(Math.random()*100);
        Cookie cookie_1 = new Cookie("userId", srn);
        Cookie cookie_2 = new Cookie("sessionId", Long.toString(sessionId));
        cookie_1.setPath("/");
        cookie_2.setPath("/");
        response.addCookie(cookie_1);
        response.addCookie(cookie_2);

        Session session = new Session();


        if(usertype=="student")
        {
            System.out.println(environment.getProperty("service_url.student")+"/"+srn);
            Student student = restTemplate.getForObject(environment.getProperty("service_url.student")+"/"+srn, Student.class);
            if(student == null) {
                System.out.println("null");
                response.sendRedirect(environment.getProperty("service_url.frontend")+"/error");
                return "login";
            }
            session.setClassIds(student.classroomIds);
            System.out.println("Classroom Ids:" + student.classroomIds);

        }
        else
        {
            Teacher teacher = restTemplate.getForObject(environment.getProperty("service_url.teacher")+"/"+srn, Teacher.class);
            if(teacher == null) {
                System.out.println("null");
                response.sendRedirect(environment.getProperty("service_url.frontend")+"/error");
                return "login";
            }
            session.setClassIds(teacher.classroomIds);
            System.out.println("Teacher Ids:" + teacher.classroomIds);
        }
        
        session.setId(sessionId);
        session.setUserId(srn);

        sessionRepository.save(session);

        response.sendRedirect(environment.getProperty("service_url.frontend")+"/"+usertype);
 
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
                        System.out.println("Student login");

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

                        if(classes.size()>0)
                        {
                            Classroom class1= classes.iterator().next();  
                            session.setClassId(class1.getId());                      
                        List<Assignment> assignment=getAssignments(session.getClassId());
                        //TBD 
                        List<List<Assignment>> classified= classifyAssignments(assignment,session);
                        List<Assignment> pending= classified.get(0);
                        List<Assignment> submitted= classified.get(1);
                        model.addAttribute("pending",pending);
                        model.addAttribute("submitted",submitted);  
                        }                  
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

    public String landingTeacher(HttpServletRequest request, HttpServletResponse response, Model model){

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
                        System.out.println("Teacher login");
                        Teacher teacher = restTemplate.getForObject(environment.getProperty("service_url.teacher")+"/"+session.getUserId(), Teacher.class);
                        System.out.println("Teacher list of class ids");
                        System.out.println(teacher.getClassroomIds());
                        // NOTE: Update any session data here
                        session.setClassIds(teacher.classroomIds);
                        sessionRepository.save(session);
                        // Fetch List of Classrooms
                        Set<Long> classIds = session.getClassIds();
                        System.out.println(classIds);
                        // classIds.add((long)1);
                        Set<Classroom> classes =  new HashSet<>();

                        classes=getclasses(classIds);

                        if(classes.size()>0)
                        {Classroom class1= classes.iterator().next();                        
                        List<Assignment> assignment=getAssignments(class1.getId());
                        //TBD 
                        
                        model.addAttribute("assignment",assignment);
                        }                  
                        model.addAttribute("classes", classes);
                        return "teacherHome";
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
        
        return "teacherHome";
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
        if(a.size()!=0)
        System.out.println(a.get(0));
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


    public String expandAssignment(String title, String description, String deadline,Model model)
    {
        //get assignment files from assignment Service

        //assignmentModel.saveAssignment(a);
        model.addAttribute("result","");
        model.addAttribute("marks","NA");

        return "assignment";
    }

    public String submitAssignment(MultipartFile file, Model model)
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

    public String changeClass(Long id){


        return "index";

    }

    public void createClass(String teacherId,String className,HttpServletResponse response) throws IOException
    {
        Classroom class1=new Classroom();
        class1.setName(className);
        class1.setTeacherId(teacherId);
        //class.setName(className);
        Classroom class2=restTemplate.postForObject(environment.getProperty("service_url.classroom")+"/save", class1, Classroom.class);
        System.out.println(class2.getId());
        String res=restTemplate.postForObject(environment.getProperty("service_url.teacher")+"/addClass/"+teacherId,class2, String.class);
        System.out.println(res);
        response.sendRedirect(environment.getProperty("service_url.frontend")+"/teacher");


    }

    public String addAssignment(Map<String,String> body,HttpServletResponse response) throws ParseException,IOException 
        {
        String title= body.get("title");
        String desc=body.get("description");
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date deadline=inFormat.parse(body.get("deadline"));
        int classid=Integer.parseInt(body.get("classid"));
        Map<String,String> tc = new HashMap<String,String>();
        int index = 0;
        for (String key : body.keySet()) {
            if (index++ < 4 )
             {continue; }
            //if(key.startsWith("input"))
            tc.put(key, body.get(key));
            //else if(key.startsWith("output"))
             //   tc.put(key, body.get(key));
   
        }
        Assignment a = new Assignment();
        a.setAssignmentTitle(title);
        a.setAssignmentDescription(desc);
        a.setDeadlineDate(deadline);
        a.setClassAssigned(classid);
        a.setTestCases(tc);

        Assignment a2=restTemplate.postForObject(environment.getProperty("service_url.assignment")+"/save", a, Assignment.class);
        response.sendRedirect(environment.getProperty("service_url.frontend")+"/teacher");
        return "Success";
    }

    public String expandAssignmentTeacher(Long id,Model model){

        Assignment a = restTemplate.getForObject(environment.getProperty("service_url.assignment")+"/id/"+id, Assignment.class);
        Map<String, Long> submissions= a.getSubmissions();
        List<Submission> sublist = new ArrayList<Submission>();

        for(String key : submissions.keySet()){
            sublist.add(getSubmission(submissions.get(key)));
        }
        model.addAttribute("submission", sublist);
        model.addAttribute("title",a.getAssignmentTitle());

        return "expandAssignment";
    }

    public Submission getSubmission(Long id)
    {
        return restTemplate.getForObject(environment.getProperty("service_url.assignment")+id, Submission.class);

    }

    public void goback(HttpServletResponse response) throws IOException
    {
        response.sendRedirect(environment.getProperty("service_url.frontend")+"/teacher");
    }
}