package com.yae.frontend.controller;
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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/index")
    public String showUserList(Model model) {
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
        ClassTemplate response=new ClassTemplate("Class a");
        AssignmentTemplate res= new AssignmentTemplate("CD",desc,"1/4/22","class a","cd",true);

        AssignmentTemplate res2= new AssignmentTemplate("MVC",desc,"22/4/22","class a","ooad",false);
        model.addAttribute("assignment1",res2);
        model.addAttribute("assignment2",res);

        model.addAttribute("classes",response);
        return "index";
    }

    
}



