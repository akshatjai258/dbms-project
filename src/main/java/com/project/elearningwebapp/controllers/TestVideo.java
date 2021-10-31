package com.project.elearningwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestVideo {
    @GetMapping("/test")
    public String getVideo(){
        return "videoPlayer";
    }
}
