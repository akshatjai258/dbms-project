package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAOImpl;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
    public String save(@ModelAttribute("category") Category category ,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        category.setLogo(fileName);
        File fi = new ClassPathResource("static/images").getFile();

        Category savedCategory = dao.save(category);

        String uploadDir = fi.getAbsolutePath() +"/category-logos/"+ savedCategory.getCategoryId();

        Path uploadPath = Paths.get(uploadDir);

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

        return "redirect:/";
    }




}
