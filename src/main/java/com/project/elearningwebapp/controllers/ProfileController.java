package com.project.elearningwebapp.controllers;


import com.project.elearningwebapp.dao.StudentDAO;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProfileController {
    @Autowired
    private StudentDAO stdao;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/user/account")
    public String viewLoggedInUserDetails(@AuthenticationPrincipal MyUserDetails loggedUser, Model model){
//        int UserId = loggedUser.getUser().getUser_id();

        User user = loggedUser.getUser();
        model.addAttribute("securityservice", securityService);
        model.addAttribute("user", user);

        if(user.getRole().equals("ROLE_STUDENT")){
            Student student = stdao.getByUserId(user.getUser_id());
            model.addAttribute("student", student);
            System.out.println(student.getStudentId());
        }
        else if(user.getRole().equals("ROLE_TEACHER")){
            model.addAttribute("userextra", null);
        }
        else{
            model.addAttribute("userextra", null);
        }

        return "loggedInUserProfile";
    }
    @PostMapping("/profile/update")
    public String saveDetails(@ModelAttribute Student student, @AuthenticationPrincipal MyUserDetails loggedUser, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException{

        student.setStudentId(stdao.getByUserId(loggedUser.getUser().getUser_id()).getStudentId());

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println(fileName);
        student.setProfilePic(fileName);

        File fi = new ClassPathResource("static/images").getFile();

        stdao.update(student);
        String uploadDir = fi.getAbsolutePath() +"/profile/"+ student.getStudentId() + "/";



        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath);



        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }


        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("file could not be saved");
        }









        return "redirect:/user/account";
    }

}
