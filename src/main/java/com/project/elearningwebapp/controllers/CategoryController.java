package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAOImpl;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    @Autowired
    CategoryDAOImpl dao;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/category/new")
    public String main(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("securityservice", securityService);
        return "AddCategory";
    }

    @PostMapping("/category/save")
    public String save(@ModelAttribute("category") Category category){
        dao.save(category);
        return "redirect:/";
    }
}
