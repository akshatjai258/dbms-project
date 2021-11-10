package com.project.elearningwebapp.controllers;

import com.project.elearningwebapp.dao.*;
import com.project.elearningwebapp.models.*;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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
import java.util.*;

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

    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private EnrollmentDAO enrollmentDAO;



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
        model.addAttribute("totalPages", (dao.advanceCount(course_, category_, difficulty_)+offset-1)/offset);
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
        Course course = dao.get(courseId);

        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        model.addAttribute("totalweeks", course.getNoOfWeeks());
        return "addTopic";
    }

    @PostMapping("/course/{courseId}/save-topic")
    public String saveTopic(@PathVariable Integer courseId,@AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("topic") Topic topic, @RequestParam("lectureVideo") MultipartFile multipartFile1, @RequestParam("lectureNotes") MultipartFile multipartFile2) throws IOException{
        System.out.println(topic);
        int current = topicDAO.getCount(courseId, topic.getWeek());
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

    @GetMapping("/course/{courseId}/preview")
    public String courseDetail(@AuthenticationPrincipal MyUserDetails loggedUser, @PathVariable Integer courseId, Model model){




        model.addAttribute("onestarcount", feedbackDAO.numberOfStars(1, courseId));
        model.addAttribute("twostarcount", feedbackDAO.numberOfStars(2, courseId));
        model.addAttribute("threestarcount", feedbackDAO.numberOfStars(3, courseId));
        model.addAttribute("fourstarcount", feedbackDAO.numberOfStars(4, courseId));
        model.addAttribute("fivestarcount", feedbackDAO.numberOfStars(5, courseId));


        Feedback feedback = new Feedback();
        Enrollment enrollment = new Enrollment();
        Boolean isEnrolled = true;

        Boolean isTeacher = false;
        if(loggedUser.getUser().getRole().equals("ROLE_TEACHER")){
            isTeacher = true;
        }
        if(isTeacher || !enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId)){
            isEnrolled = false;
        }


        Boolean isFeedbackGiven = true;

        if(isTeacher || !feedbackDAO.isFeedbackGiven(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId)){
            isFeedbackGiven = false;
        }
        model.addAttribute("isFeedbackGiven", isFeedbackGiven);
        System.out.println(isFeedbackGiven);
        if(isFeedbackGiven){
            model.addAttribute("filledfeedback", feedbackDAO.getByStudentAndCourseId(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId));
        }


        model.addAttribute("isTeacher", isTeacher);

        Teacher author =  dao.get(courseId).getTeacher();
        model.addAttribute("author", author);
        model.addAttribute("isEnrolled", isEnrolled);

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("securityservice", securityService);
        Course course = dao.get(courseId);
        model.addAttribute("feedback", feedback);

        model.addAttribute("course", course);

        model.addAttribute("totallectures", topicDAO.getNoOflectures(courseId));
        model.addAttribute("totalenrollments", enrollmentDAO.getNoOfStudentsEnrolled(courseId));


        List<Topic>topics = topicDAO.findByCourseID(courseId);
        ArrayList<ArrayList<Topic>> ans = new ArrayList<ArrayList<Topic>>(course.getNoOfWeeks());
        for(int i=1;i<=course.getNoOfWeeks()+1;i++){
            ans.add(new ArrayList<>());
        }
        for (Topic t:topics) {
            ArrayList<Topic>current = ans.get(t.getWeek());
            current.add(t);
            ans.set(t.getWeek(), current);
        }

        for(int i=1;i<=course.getNoOfWeeks() + 1;i++){
            Collections.sort(ans.get(i-1),new Comparator<Topic>() {
                @Override
                public int compare(Topic s1, Topic s2) {
                    return Integer.compare(s1.getTopicNumber(), s2.getTopicNumber());
                }
            });
        }

        model.addAttribute("curriculum", ans);


        List<Feedback>feedbacks = feedbackDAO.getTopFeedbacks(courseId);
        model.addAttribute("feedbacks", feedbacks);

        // if the current user is the author of this course
        Boolean isauthor = false;
        if(isTeacher && tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) == course.getTeacher().getTeacherId()){
            isauthor=true;
        }

        model.addAttribute("isauthor", isauthor);
        return "courseDetail";
    }

    @PostMapping("/course/{courseId}/save-feedback")
    public String saveFeedback(@PathVariable Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("feedback") Feedback feedback){
        System.out.println(feedback);
        System.out.println(loggedUser.getUser());
        if(loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            feedback.setTimestamp(new Timestamp(System.currentTimeMillis()));
            feedback.setStudent(studentDAO.getByUserId(loggedUser.getUser().getUser_id()));
            feedback.setCourse(dao.get(courseId));
            System.out.println(feedback);
            feedbackDAO.save(feedback);
        }

        return "redirect:/";
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(@PathVariable Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("enrollment") Enrollment enrollment){
        enrollment.setCourse(dao.get(courseId));
        enrollment.setStudent(studentDAO.getByUserId(loggedUser.getUser().getUser_id()));
        enrollment.setEnrollment_time(new Timestamp(System.currentTimeMillis()));
        enrollmentDAO.enroll(enrollment);
        return "redirect:/course/"+courseId +"/preview/";
    }

    @GetMapping("/feedbacks/all/{courseId}")
    public String getAllFeedbacks(@PathVariable("courseId") Integer courseId, Model model){
        List<Feedback>feedbacks = feedbackDAO.getByCourseId(courseId);
        System.out.println(feedbacks);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("securityservice", securityService);
        return "feedbacks";
    }

    @PostMapping("/feedback/edit/{courseId}")

    public String editFeedback(@PathVariable("courseId") Integer courseId, @ModelAttribute("feedback") Feedback feedback, @AuthenticationPrincipal MyUserDetails loggedUser){
        System.out.println(feedback);
        Feedback oldFeedback = feedbackDAO.getByStudentAndCourseId(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId);
        feedback.setFeedbackId(oldFeedback.getFeedbackId());
        feedback.setCourse(oldFeedback.getCourse());
        feedback.setStudent(oldFeedback.getStudent());
        feedbackDAO.update(feedback);
        return "redirect:/";
    }

    @GetMapping("/course/edit-details/{courseId}")
    public String editCourseDetails(@PathVariable("courseId") Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser, Model model){
        // find the course with courseId

        if(tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) != dao.get(courseId).getTeacher().getTeacherId()){
            return "redirect:/";
        }
        Course course = dao.get(courseId);
        model.addAttribute("course", course);
        model.addAttribute("securityservice", securityService);
        List<Category>categories = cat_dao.findAll();
        model.addAttribute("categories", categories);
        return "editCourse";

    }

    @PostMapping("/course/update/{courseId}")
    public String updateCourseDetails(@PathVariable("courseId") Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("course") Course course, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException{
        Course oldCourse = dao.get(courseId);

        course.setTimestamp(oldCourse.getTimestamp());

        User user = loggedUser.getUser();
        if(user.getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }
        Teacher teacher = tdao.getByUserId(user.getUser_id());
        System.out.println(teacher);
        course.setTeacher(teacher);
        Category category = cat_dao.getById(course.getCategory().getCategoryId());
        course.setCategory(category);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        File fi = new ClassPathResource("static/images").getFile();
        System.out.println(course);

        if(multipartFile.isEmpty()){

            fileName = course.getCourseThumbnail();
        }
        else{
            String uploadDir = fi.getAbsolutePath() +"/course-thumbnails/"+ courseId;

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
        }

        course.setCourseThumbnail(fileName);
        dao.update(course);



        return "redirect:/";
    }


}
