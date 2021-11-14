package com.project.elearningwebapp.controllers;


import com.project.elearningwebapp.dao.CourseDAO;
import com.project.elearningwebapp.dao.EnrollmentDAO;
import com.project.elearningwebapp.dao.TeacherDAO;
import com.project.elearningwebapp.dao.TopicDAO;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TopicDAO topicDAO;

    @GetMapping("/courses/teacher/{teacherId}/{page}")
    public String Mycourses(@PathVariable(value = "page") Integer page, @PathVariable(value = "teacherId") Integer teacherID, @AuthenticationPrincipal MyUserDetails loggedUser, Model model){
        Teacher teacher = teacherDAO.get(teacherID);

        Boolean isauthor = false;
        if(loggedUser.getUser().getRole().equals("ROLE_TEACHER") && loggedUser.getUser().getUser_id() == teacher.getUser().getUser_id()){

            isauthor = true;
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("isauthor", isauthor);

        model.addAttribute("securityservice", securityService);
        int offset = 3;
        model.addAttribute("currentPage", page);

        PageRequest pageable = PageRequest.of(page, offset);
        Page<Course> courses = courseDAO.getAllByTeacherId(teacher.getTeacherId(), pageable);
        model.addAttribute("totalPages", (courseDAO.count_new(teacher.getTeacherId())+offset-1)/offset);
        model.addAttribute("courses", courses);
        System.out.println((courseDAO.count_new(teacher.getTeacherId())+offset-1)/offset);
        ArrayList<Object>stuff = new ArrayList<Object>();
        for (Course c: courses) {
            stuff.add( new Object[]{ enrollmentDAO.getNoOfStudentsEnrolled(c.getCourseId()), topicDAO.getNoOflectures(c.getCourseId())});
        }
        model.addAttribute("stuff", stuff);

        return "teacherCourses";
    }

    @GetMapping("/user/teacher/profile/{userId}")
    public String viewTeacherProfile(Model model, @PathVariable Integer userId){
        Teacher teacher = teacherDAO.getByUserId(userId);
        Integer teacherId = teacherDAO.getTeacherIdByUserId(userId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("securityservice", securityService);
        model.addAttribute("numberOfCourses", courseDAO.count_new(teacherId));
        model.addAttribute("numberOfLectures", courseDAO.numOfLectures(teacherId));
        int offset = 6;
        PageRequest pageable = PageRequest.of(0, offset);
        Page<Course> courses = courseDAO.getAllByTeacherId(teacher.getTeacherId(), pageable);
        model.addAttribute("courses", courses);
        List<Object>enrolmentcount = new ArrayList<Object>();
        for(Course c:courses){
            Integer x = enrollmentDAO.getNoOfStudentsEnrolled(c.getCourseId());
            Integer z = topicDAO.getNoOflectures(c.getCourseId());

            enrolmentcount.add(new Object[]{x,z});
        }
        model.addAttribute("enrolmentcount", enrolmentcount);
        return "teacherProfile";

    }
}
