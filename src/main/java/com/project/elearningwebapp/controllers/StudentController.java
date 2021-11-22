package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.EnrollmentDAO;
import com.project.elearningwebapp.dao.StudentDAO;
import com.project.elearningwebapp.dao.TopicDAO;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    @Autowired
    private TopicDAO topicDAO;


    @GetMapping("/student/my-courses/{page}")
    public String getMyCourses(Model model, @AuthenticationPrincipal MyUserDetails loggedUser, @PathVariable(value = "page") Integer page){
        model.addAttribute("securityservice", securityService);
        Student student = studentDAO.getByUserId(loggedUser.getUser().getUser_id());

        int offset = 3;
        model.addAttribute("currentPage", page);
        PageRequest pageable = PageRequest.of(page, offset);


        Page<Course> courses = enrollmentDAO.coursesEnrolled(student.getStudentId(), pageable);
        model.addAttribute("courses", courses);
        List<Course>c1 = courses.getContent();
        System.out.println(c1);
        ArrayList<Object> stuff = new ArrayList<Object>();
        for (Course c: c1) {
            stuff.add( new Object[]{ enrollmentDAO.getNoOfStudentsEnrolled(c.getCourseId()), topicDAO.getNoOflectures(c.getCourseId())});
        }
        model.addAttribute("stuff", stuff);
        model.addAttribute("totalPages", (enrollmentDAO.count_new(student.getStudentId()) + offset -1)/offset);

        return "studentCourses";

    }
}
