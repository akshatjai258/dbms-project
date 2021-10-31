package com.project.elearningwebapp.controllers;


import com.project.elearningwebapp.dao.StudentDAO;
import com.project.elearningwebapp.dao.TeacherDAO;
import com.project.elearningwebapp.dao.userDAO;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.models.Teacher;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ProfileController {
    @Autowired
    private StudentDAO stdao;

    @Autowired
    private userDAO udao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TeacherDAO tdao;



    @GetMapping("/user/account")
    public String viewLoggedInUserDetails(@AuthenticationPrincipal MyUserDetails loggedUser, Model model){

        User user = loggedUser.getUser();

        model.addAttribute("securityservice", securityService);
        model.addAttribute("user", user);

        if(user.getRole().equals("ROLE_STUDENT")){
            return "redirect:/student/account";

        }
        else if(user.getRole().equals("ROLE_TEACHER")){
            return "redirect:/teacher/account";
        }
        else{
            return  "redirect:/admin/account";
        }
    }


    @GetMapping("/student/account")
    public String viewStudentProfile(@AuthenticationPrincipal MyUserDetails loggedUser, Model model){

        User user = loggedUser.getUser();

        model.addAttribute("securityservice", securityService);
        model.addAttribute("user", user);

        if(user.getRole().equals("ROLE_STUDENT")){
            Student student = stdao.getByUserId(user.getUser_id());
            model.addAttribute("student", student);

        }
        else{
            return "redirect:/user/account";
        }

        return "LoggedStudentProfile";
    }

    @PostMapping("student/profile/update")
    public String updateStudentProfile(@ModelAttribute("student") Student student, @ModelAttribute("user") User user, @AuthenticationPrincipal MyUserDetails loggedUser, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException{

        student.setStudentId(stdao.getByUserId(loggedUser.getUser().getUser_id()).getStudentId());
        user.setUser_id(loggedUser.getUser().getUser_id());

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        udao.update(user);
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setEmailId(user.getEmailId());


        if(student.getDateOfBirth().equals("")){
            student.setDateOfBirth(stdao.getByUserId(loggedUser.getUser().getUser_id()).getDateOfBirth());
        }
        if(multipartFile.isEmpty()){

            fileName = stdao.getByUserId(loggedUser.getUser().getUser_id()).getProfilePic();
        }
        else{
            Student stud = stdao.getByUserId(loggedUser.getUser().getUser_id());
            int x = stud.getNoOfPhotosUploaded();
            x++;
            String id = stud.getStudentId() + "_" + Integer.toString(x);
            student.setNoOfPhotosUploaded(x);
            fileName = id+"_"+fileName;

            File fi = new ClassPathResource("static/images").getFile();

            String uploadDir = fi.getAbsolutePath() +"/profile/student/";

            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);

                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new IOException("file could not be saved");
            }
        }

        student.setProfilePic(fileName);

        stdao.update(student);
        return "redirect:/user/account";
    }



    @GetMapping("/teacher/account")
    public String viewTeacherProfile(@AuthenticationPrincipal MyUserDetails loggedUser, Model model){

        User user = loggedUser.getUser();



        model.addAttribute("securityservice", securityService);
        model.addAttribute("user", user);

        if(user.getRole().equals("ROLE_TEACHER")){
            Teacher teacher = tdao.getByUserId(user.getUser_id());
            model.addAttribute("teacher", teacher);
        }
        else{
            return "redirect:/user/account";
        }

        return "LoggedTeacherProfile";
    }

    @PostMapping("teacher/profile/update")
    public String updateTeacherDetails(@ModelAttribute("teacher") Teacher teacher, @ModelAttribute("user") User user, @AuthenticationPrincipal MyUserDetails loggedUser, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException{

        System.out.println("hello");
        teacher.setTeacherId(tdao.getByUserId(loggedUser.getUser().getUser_id()).getTeacherId());
        user.setUser_id(loggedUser.getUser().getUser_id());
        System.out.println(teacher);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        udao.update(user);
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setEmailId(user.getEmailId());


        if(multipartFile.isEmpty()){
            fileName = tdao.getByUserId(loggedUser.getUser().getUser_id()).getProfilePic();
        }
        else{
            Teacher teacher1 = tdao.getByUserId(loggedUser.getUser().getUser_id());
            int x = teacher1.getNoOfPhotosUploaded();
            x++;
            String id = teacher1.getTeacherId() + "_" + Integer.toString(x);
            teacher.setNoOfPhotosUploaded(x);
            fileName = id+"_"+fileName;

            File fi = new ClassPathResource("static/images").getFile();

            String uploadDir = fi.getAbsolutePath() +"/profile/teacher/";

            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);

                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new IOException("file could not be saved");
            }
        }

        teacher.setProfilePic(fileName);

        tdao.update(teacher);
        return "redirect:/user/account";
    }



}
