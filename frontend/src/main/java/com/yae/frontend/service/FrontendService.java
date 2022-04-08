package com.yae.frontend.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yae.frontend.entity.Session;
import com.yae.frontend.repository.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class FrontendService {

    @Autowired 
    private SessionRepository sessionRepository;
    
    private final RestTemplate restTemplate = new RestTemplate();

    // public FrontendService(RestTemplateBuilder restTemplateBuilder) {
    //     this.restTemplate = restTemplateBuilder.build();
    // }


    public ResponseEntity<Integer> postForObject(MultipartFile file) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);



        MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,Object>> entity = new HttpEntity<MultiValueMap<String,Object>>(map,headers);


        String url="https://localhost:5000";
        System.out.println("here");
        return restTemplate.postForEntity(url, entity, Integer.class);
    }
     
      
    public String login(String srn, HttpServletResponse response){
        Long sessionId = Math.round(Math.random()*100);
        Cookie cookie_1 = new Cookie("userId", srn);
        Cookie cookie_2 = new Cookie("sessionId", Long.toString(sessionId));
        cookie_1.setPath("/");
        cookie_2.setPath("/");
        response.addCookie(cookie_1);
        response.addCookie(cookie_2);

        Session session = new Session();
        session.setId(sessionId);
        session.setUserId(srn);
        sessionRepository.save(session);
        
        try {
            response.sendRedirect("http://localhost:8080/");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return "login";
        
    }

    
    public String landing(HttpServletRequest request, HttpServletResponse response, Model model){

        Cookie [] cookies = request.getCookies();

        if(cookies == null)                 
        { 
            try {
                response.sendRedirect("http://localhost:8080/login");
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
                        model.addAttribute("session", session);
                        return "index";
                    }
                }
            }
            try {
                response.sendRedirect("http://localhost:8080/login");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        
        return "index";
    }
}