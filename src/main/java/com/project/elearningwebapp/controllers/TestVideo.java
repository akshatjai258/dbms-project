package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestVideo {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/test")

    public String getVideo(Model model){
        System.out.println(securityService.findLoggedInUserId());
        model.addAttribute("securityservice", securityService);
        return "contentPresenter";
    }
}
