package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAO;
import com.project.elearningwebapp.dao.CourseDAO;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
    @Autowired
    private CourseDAO dao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CategoryDAO cat_dao;

    @GetMapping("/courses/explore/{page}")
    public String courseExplore(@PathVariable(value = "page") Integer page, Model model,
                                @RequestParam(value = "course_", required = false) String course_,
                                @RequestParam(value = "category_", required = false) String category_,
                                @RequestParam(value = "difficulty_", required = false) String difficulty_){
        int offset = 3;
        System.out.println(category_);
        System.out.println(course_);

        if(course_ == null){
            course_ = "";
        }
        if(category_==null){
            category_ ="Any";
        }

        if(difficulty_ == null){
            difficulty_ = "Any";
        }

        PageRequest pageable = PageRequest.of(page, offset, Sort.Direction.fromString("DESC"), "avg_rating");
        Page<Course> courses = dao.advanceFilter(course_, category_, difficulty_, pageable);
        String queryString = "?course_=" +((course_==null)?"":(course_))+ "&category_="+category_+"&difficulty_="+difficulty_;

        List<Category>categories = cat_dao.findAll();
        model.addAttribute("securityservice", securityService);
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (dao.count()+offset-1)/offset);
        model.addAttribute("categories", categories);
        model.addAttribute("queryString", queryString);


        return "courseExplore";
    }

    @GetMapping("/course/new")
    public String createCourse(Model model){

        return "createCourse";
    }
}
