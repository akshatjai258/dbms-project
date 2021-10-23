package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.StudentDAO;
import com.project.elearningwebapp.dao.userDAO;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.services.SecurityService;
import com.project.elearningwebapp.services.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserAuthController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private userDAO dao;

    @Autowired
    private StudentDAO stdao;


    @GetMapping("/user/login")
    public String showLoginForm(Model model){
        model.addAttribute("securityservice", securityService);

        if(securityService.findLoggedInUserId() == 0){
            return "login";
        }

        return "redirect:/";
    }




    @GetMapping("/user/register")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("securityservice", securityService);
        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/user/save")
    public String SaveUser(@ModelAttribute("user") User user){
        user.setEnabled(true);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = dao.save(user);

        System.out.println(savedUser.getRole());
        if(savedUser.getRole().equals("ROLE_STUDENT")){

            int stId = stdao.getStudentIdByUserId(savedUser.getUser_id());
            Student savedStudent = stdao.getByUserId(savedUser.getUser_id());

            savedStudent.setStudentId(stId);
        }

        return "redirect:/";
    }
}
