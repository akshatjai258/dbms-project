package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.CategoryDAO;
import com.project.elearningwebapp.dao.CourseDAO;
import com.project.elearningwebapp.dao.TeacherDAO;
import com.project.elearningwebapp.dao.TopicDAO;
import com.project.elearningwebapp.models.*;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
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

    @Autowired
    private TeacherDAO tdao;

    @Autowired
    private TopicDAO topicDAO;



    @GetMapping("/course/explore/{page}")
    public String courseExplore(@PathVariable(value = "page") Integer page, Model model,
                                @RequestParam(value = "course_", required = false) String course_,
                                @RequestParam(value = "category_", required = false) String category_,
                                @RequestParam(value = "difficulty_", required = false) String difficulty_,
                                @RequestParam(value = "sorted_by", required = false) String sorted_by){
        int offset = 3;


        if(course_ == null){
            course_ = "";
        }
        if(category_==null){
            category_ ="Any";
        }

        if(difficulty_ == null){
            difficulty_ = "Any";
        }
        String sorting_order="DESC";
        if(sorted_by == null || sorted_by.equals("Default")){
            sorted_by="course_id";
            sorting_order = "ASC";
        }
        if(sorted_by.equals("Recent First")){
            sorted_by="timestamp";
            sorting_order = "DESC";
        }
        if(sorted_by.equals("Cheapest First")){
            sorted_by = "course_price";
            sorting_order="ASC";
        }
        if(sorted_by.equals("Rating")){
            sorted_by="avg_rating";
            sorting_order="DESC";
        }

        PageRequest pageable = PageRequest.of(page, offset, Sort.Direction.fromString(sorting_order), sorted_by);
        Page<Course> courses = dao.advanceFilter(course_, category_, difficulty_, pageable);
        String queryString = "?course_=" +((course_==null)?"":(course_))+ "&category_="+category_+"&difficulty_="+difficulty_ + "&sorted_by=" + sorted_by;

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
        Course course = new Course();
        model.addAttribute("course", course);
        List<Category>categories = cat_dao.findAll();

        model.addAttribute("securityservice", securityService);
        model.addAttribute("categories", categories);

        return "createCourse";
    }

    @PostMapping("/course/save")
    public String saveCourse(@AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("course") Course course, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException{
        course.setTimestamp(new Timestamp(System.currentTimeMillis()));
        User user = loggedUser.getUser();
        if(user.getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }
        Teacher teacher = tdao.getByUserId(user.getUser_id());
        course.setTeacher(teacher);
        Category category = cat_dao.getById(course.getCategory().getCategoryId());
        course.setCategory(category);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        course.setCourseThumbnail(fileName);
        File fi = new ClassPathResource("static/images").getFile();
        System.out.println(course);

        Course savedCourse = dao.save(course);

        String uploadDir = fi.getAbsolutePath() +"/course-thumbnails/"+ savedCourse.getCourseId();

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

    @GetMapping("/course/{courseId}/add-topic")

    public String addTopic(@PathVariable Integer courseId, Model model){
        System.out.println(courseId);
        Topic topic = new Topic();
        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        return "addTopic";
    }

    @PostMapping("/course/{courseId}/save-topic")
    public String saveTopic(@PathVariable Integer courseId,@AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("topic") Topic topic, @RequestParam("lectureVideo") MultipartFile multipartFile1, @RequestParam("lectureNotes") MultipartFile multipartFile2) throws IOException{
        System.out.println(topic);
        int current = topicDAO.getCount(courseId);
        topic.setTopicNumber(current+1);
        topic.setCourse(dao.get(courseId));

        String videolecture = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        String notes = StringUtils.cleanPath(multipartFile2.getOriginalFilename());

        topic.setTopicLecture(videolecture);
        topic.setTopicNotes(notes);

        File fi1 = new ClassPathResource("static/videos").getFile();
        File fi2 = new ClassPathResource("static/notes").getFile();

        Topic savedTopic = topicDAO.save(topic);


        String VideouploadDir = fi1.getAbsolutePath() + "/" + courseId +"/";
        String NotesuploadDir = fi2.getAbsolutePath() + "/" + courseId +"/";
        Path VideouploadPath = Paths.get(VideouploadDir);
        Path NotesuploadPath = Paths.get(NotesuploadDir);

        if(!Files.exists(VideouploadPath)){
            Files.createDirectories(VideouploadPath);
        }

        if(!Files.exists(NotesuploadPath)){
            Files.createDirectories(NotesuploadPath);
        }



        try {
            InputStream inputStream = multipartFile1.getInputStream();
            Path filePath = VideouploadPath.resolve(videolecture);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("video could not be saved");
        }

        try {
            InputStream inputStream = multipartFile2.getInputStream();
            Path filePath = NotesuploadPath.resolve(notes);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("notes could not be saved");
        }

        return "redirect:/";
    }

    @GetMapping("/course/{courseId}")
    public String courseDetail(@PathVariable Integer courseId, Model model){
        model.addAttribute("securityservice", securityService);
        Course course = dao.get(courseId);
        System.out.println(course);
        model.addAttribute("course", course);

        return "courseDetail";
    }


}
