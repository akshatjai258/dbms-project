package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAO;
import com.project.elearningwebapp.dao.CourseDAO;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private CategoryDAO dao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CourseDAO coursedao;

    @GetMapping("/")
    public String main(Model model){
        List<Category> categories = dao.findAll();


        PageRequest pageable = PageRequest.of(0, 6);
        Page<Course> courses = coursedao.findAll(pageable);

        model.addAttribute("securityservice", securityService);
        model.addAttribute("courses", courses);
        System.out.println(courses);
        model.addAttribute("categories", categories);


        return "home";
    }
}



