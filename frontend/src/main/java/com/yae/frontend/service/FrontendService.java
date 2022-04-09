package com.yae.frontend.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.entity.Session;
import com.yae.frontend.repository.SessionRepository;
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

        Student student = restTemplate.getForObject(environment.getProperty("service_url.student")+"/"+srn, Student.class);
        if(student == null) {
            response.sendRedirect(environment.getProperty("service_url.frontend")+"/error");
            return "login";
        }
        
        Session session = new Session();
        session.setId(sessionId);
        session.setUserId(srn);
        session.setClassIds(student.ClassroomIds);
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

                        // TO-DO: Home Page needs, list of assignments and list of classes for given student.
                        // List<Long>      
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


    public ResponseEntity<String> joinClass(String userId, Long classId, HttpServletResponse response) throws IOException {

        JSONObject classJoinReq = new JSONObject();
        classJoinReq.put("userId", userId);
        classJoinReq.put("classId", classId);
        
        HttpEntity<String> request = new HttpEntity<>(classJoinReq.toJSONString());

        response.sendRedirect(environment.getProperty("service_url.frontend"));
        return restTemplate.postForEntity(environment.getProperty("service_url.student")+"/join", request, String.class);


    }
}