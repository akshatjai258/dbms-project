package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAO;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryDAO dao;

    @Autowired
    private SecurityService securityService;
    @GetMapping("/")
    public String main(Model model){
        List<Category> categories = dao.findAll();
        model.addAttribute("securityservice", securityService);

        model.addAttribute("categories", categories);

        return "home";
    }
}



