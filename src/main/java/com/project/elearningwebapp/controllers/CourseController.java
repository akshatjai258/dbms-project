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
import java.sql.Array;
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

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private QuizAttemptDAO quizAttemptDAO;



    @GetMapping("/course/explore/{page}")
    public String courseExplore(@PathVariable(value = "page") Integer page, Model model,
                                @RequestParam(value = "course_", required = false) String course_,
                                @RequestParam(value = "category_", required = false) Integer category_,
                                @RequestParam(value = "difficulty_", required = false) String difficulty_,
                                @RequestParam(value = "sorted_by", required = false) String sorted_by){
        int offset = 4;


        if(course_ == null){
            course_ = "";
        }
        if(category_==null){
            category_ =0;
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
        System.out.println(queryString);
        List<Category>categories = cat_dao.findAll();
        List<Object>enrolmentcount = new ArrayList<Object>();
        for(Course c:courses){
            Integer x = enrollmentDAO.getNoOfStudentsEnrolled(c.getCourseId());
            Integer z = topicDAO.getNoOflectures(c.getCourseId());

            enrolmentcount.add(new Object[]{x,z});
        }
        model.addAttribute("enrolmentcount", enrolmentcount);
        model.addAttribute("securityservice", securityService);
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (dao.advanceCount(course_, category_, difficulty_)+offset-1)/offset);
        model.addAttribute("categories", categories);
        model.addAttribute("queryString", queryString);

        String mainDir = System.getProperty("user.dir") + "/target/classes/static/";
        model.addAttribute("mainDir", mainDir);
        return "courseExplore";
    }

    @GetMapping("/course/new")
    public String createCourse(Model model, @AuthenticationPrincipal MyUserDetails loggedUser){
        // Requirement :: user must be a teacher

        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }


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
    public String addTopic(@PathVariable Integer courseId, Model model, @AuthenticationPrincipal MyUserDetails loggedUser){
        // Requirement user must be author of course
        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }
        // check if current user is author





        System.out.println(courseId);
        Topic topic = new Topic();
        Course course = dao.get(courseId);

        if(course.getTeacher().getUser().getUser_id() != loggedUser.getUser().getUser_id()){
            return "redirect:/";
        }
        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        model.addAttribute("totalweeks", course.getNoOfWeeks());
        return "addTopic";
    }

    @PostMapping("/course/{courseId}/save-topic")
    public String saveTopic(@PathVariable Integer courseId,@AuthenticationPrincipal MyUserDetails loggedUser, @ModelAttribute("topic") Topic topic, @RequestParam("lectureVideo") MultipartFile multipartFile1) throws IOException{
        System.out.println(topic);
        int current = topicDAO.getCount(courseId, topic.getWeek());
        topic.setTopicNumber(current+1);
        topic.setCourse(dao.get(courseId));

        String videolecture = StringUtils.cleanPath(multipartFile1.getOriginalFilename());

        topic.setTopicLecture(videolecture);

        File fi1 = new ClassPathResource("static/videos").getFile();

        Topic savedTopic = topicDAO.save(topic);


        String VideouploadDir = fi1.getAbsolutePath() + "/" + courseId +"/";
        Path VideouploadPath = Paths.get(VideouploadDir);

        if(!Files.exists(VideouploadPath)){
            Files.createDirectories(VideouploadPath);
        }





        try {
            InputStream inputStream = multipartFile1.getInputStream();
            Path filePath = VideouploadPath.resolve(videolecture);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("video could not be saved");
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

        Boolean notLoggedIn = false;
        if(loggedUser == null){
            notLoggedIn = true;
        }

        model.addAttribute("notLoggedIn", notLoggedIn);
        Feedback feedback = new Feedback();
        Enrollment enrollment = new Enrollment();
        Boolean isEnrolled = true;

        Boolean isTeacher = false;
        if(loggedUser!=null && loggedUser.getUser().getRole().equals("ROLE_TEACHER")){
            isTeacher = true;
        }
        if(loggedUser==null || isTeacher || !enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId)){
            isEnrolled = false;
        }


        Boolean isFeedbackGiven = true;

        if(loggedUser == null ||  isTeacher || !feedbackDAO.isFeedbackGiven(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId)){
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
        if(loggedUser != null && isTeacher && tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) == course.getTeacher().getTeacherId()){
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
            Feedback savedFeedback = feedbackDAO.save(feedback);
            // update the course on which feedback is given

            Course course = dao.get(courseId);
            double x=0;
            List<Feedback>feedbacks = feedbackDAO.getByCourseId(courseId);
            Integer sum =0, cnt = 0;
            for(Feedback f:feedbacks){
                sum += f.getStar();
                cnt++;
            }
            x = (double)sum/cnt;
            System.out.println(x);
            System.out.println(dao.get(courseId));
            course.setAvgRating(x);
            System.out.println(dao.get(courseId));
            dao.updateRating(courseId, x);
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

        Course course = dao.get(courseId);
        double x=0;
        List<Feedback>feedbacks = feedbackDAO.getByCourseId(courseId);
        Integer sum =0, cnt = 0;
        for(Feedback f:feedbacks){
            sum += f.getStar();
            cnt++;
        }
        x = (double)sum/cnt;
        System.out.println(x);
        course.setAvgRating(x);
        dao.updateRating(courseId, x);

        return "redirect:/";
    }

    @GetMapping("/course/edit-details/{courseId}")
    public String editCourseDetails(@PathVariable("courseId") Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser, Model model){
        // find the course with courseId

        if(loggedUser==null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }

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

            fileName = oldCourse.getCourseThumbnail();
        }
        else{
            String uploadDir = fi.getAbsolutePath() +"/course-thumbnails/"+ courseId +"/";

            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }


            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                System.out.println(filePath);
                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new IOException("file could not be saved");
            }
        }

        System.out.println(fileName);
        course.setCourseThumbnail(fileName);
        dao.update(course);



        return "redirect:/";
    }


    @GetMapping("/course/{courseId}/content")
    public String mainCourseView(Model model, @PathVariable("courseId") int courseId, @AuthenticationPrincipal MyUserDetails loggedUser){

        // Requirement :: user must be either student enrolled in the course or he must be teacher who created this course

        Course course = dao.get(courseId);
        if(loggedUser == null){
            return "redirect:/";
        }

        if(loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            if(!enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseId)){
                return "redirect:/";
            }
        }
        else{
            if(tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) == course.getTeacher().getTeacherId()){

            }
            else{
                return "redirect:/";
            }
        }

        model.addAttribute("securityservice",securityService);

        model.addAttribute("course", course);

        Teacher teacher = course.getTeacher();
        List<Topic>topics = topicDAO.findByCourseID(courseId);
        List<Quiz>quizzes = quizDAO.findAllByCourseId(courseId);

        ArrayList<ArrayList<Topic>> ans = new ArrayList<ArrayList<Topic>>(course.getNoOfWeeks());
        ArrayList<ArrayList<Quiz>> ans2 = new ArrayList<ArrayList<Quiz>>(course.getNoOfWeeks());
        ArrayList<ArrayList<Boolean>>attempts = new ArrayList<ArrayList<Boolean>>(course.getNoOfWeeks());

        for(int i=1;i<=course.getNoOfWeeks()+1;i++){
            ans.add(new ArrayList<>());
            ans2.add(new ArrayList<>());
            attempts.add(new ArrayList<>());
        }
        for (Topic t:topics) {
            ArrayList<Topic>current = ans.get(t.getWeek());
            current.add(t);
            ans.set(t.getWeek(), current);
        }

        for (Quiz q:quizzes) {
            ArrayList<Quiz>current = ans2.get(q.getWeek());


            current.add(q);
            ans2.set(q.getWeek(), current);
        }

        if(loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            for (Quiz q:quizzes) {

                ArrayList<Boolean>cur = attempts.get(q.getWeek());
                QuizAttempt quizAttempt = quizAttemptDAO.getByStudentAndQuizID(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), q.getQuizId());
                if(quizAttempt == null || quizAttempt.equals(null)){

                    cur.add(false);
                }
                else{
                    cur.add(true);
                }
                attempts.set(q.getWeek(), cur);
            }
            System.out.println(attempts);

        }

        for(int i=1;i<=course.getNoOfWeeks() + 1;i++){
            Collections.sort(ans.get(i-1),new Comparator<Topic>() {
                @Override
                public int compare(Topic s1, Topic s2) {
                    return Integer.compare(s1.getTopicNumber(), s2.getTopicNumber());
                }
            });

        }
        model.addAttribute("quizzes", ans2);
        model.addAttribute("attempts", attempts);
        Boolean isauthor = false;
        if(loggedUser.getUser().getRole().equals("ROLE_TEACHER") && loggedUser.getUser().getUser_id() == teacher.getUser().getUser_id()){

            isauthor = true;
        }
        model.addAttribute("isauthor", isauthor);
        System.out.println(isauthor);




        model.addAttribute("curriculum", ans);


        return "mainCourseView";

    }

    @GetMapping("/course/video/{topicId}")

    public String getVideo(Model model, @PathVariable Integer topicId, @AuthenticationPrincipal MyUserDetails loggedUser){


        Topic topic = topicDAO.get(topicId);
        // find the course in which this topic belongs

        Course course = dao.get(topic.getCourse().getCourseId());

        if(loggedUser == null){
            return "redirect:/";
        }

        if(loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            if(!enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), course.getCourseId())){
                return "redirect:/";
            }
        }
        else{
            if(tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) == course.getTeacher().getTeacherId()){

            }
            else{
                return "redirect:/";
            }
        }




        model.addAttribute("course", course);
        System.out.println(securityService.findLoggedInUserId());
        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        Boolean isvideo = true;
        model.addAttribute("isvideo", isvideo);
        System.out.println(isvideo);
        return "contentPresenter";
    }
    @GetMapping("/course/notes/{topicId}")
    public String getNotes(Model model, @PathVariable Integer topicId, @AuthenticationPrincipal MyUserDetails loggedUser){
        Topic topic = topicDAO.get(topicId);
        Course course = dao.get(topic.getCourse().getCourseId());


        if(loggedUser == null){
            return "redirect:/";
        }

        if(loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            if(!enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), course.getCourseId())){
                return "redirect:/";
            }
        }
        else{
            if(tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) == course.getTeacher().getTeacherId()){

            }
            else{
                return "redirect:/";
            }
        }

        model.addAttribute("course", course);
        System.out.println(securityService.findLoggedInUserId());
        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        Boolean isvideo = false;
        System.out.println(isvideo);
        model.addAttribute("isvideo", isvideo);
        return "contentPresenter";
    }

    @GetMapping("/course/edit-topic/{topicId}")
    public String editTopic(Model model, @PathVariable Integer topicId, @AuthenticationPrincipal MyUserDetails loggedUser){
        // Requirement :: user must be author of the course



        Topic topic = topicDAO.get(topicId);
        Course course = dao.get(topic.getCourse().getCourseId());

        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }


        if(tdao.getTeacherIdByUserId(loggedUser.getUser().getUser_id()) != dao.get(course.getCourseId()).getTeacher().getTeacherId()){
            return "redirect:/";
        }
        model.addAttribute("topic", topic);
        model.addAttribute("securityservice", securityService);
        return "editTopic";

    }

    @PostMapping("/course/update-topic/{topicId}")
    public String updateTopic(@PathVariable Integer topicId, @ModelAttribute("topic") Topic topic, @RequestParam("lectureVideo") MultipartFile multipartFile) throws IOException{
        Topic t1 = topicDAO.get(topicId);

        Course course = t1.getCourse();
        System.out.println(t1);
        System.out.println(topic);
        String videolecture = StringUtils.cleanPath(multipartFile.getOriginalFilename());


        File fi1 = new ClassPathResource("static/videos").getFile();
        topic.setTopicId(t1.getTopicId());
        topic.setCourse(course);
        topic.setTopicNumber(t1.getTopicNumber());
        topic.setWeek(t1.getWeek());



        if(multipartFile.isEmpty()){
            videolecture = t1.getTopicLecture();
        }
        else{
            String VideouploadDir = fi1.getAbsolutePath() + "/" + course.getCourseId() +"/";
            Path VideouploadPath = Paths.get(VideouploadDir);

            if(!Files.exists(VideouploadPath)){
                Files.createDirectories(VideouploadPath);
            }

            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = VideouploadPath.resolve(videolecture);
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new IOException("video could not be saved");
            }
        }
        topic.setTopicLecture(videolecture);
        System.out.println(topic);

        topicDAO.update(topic);


        return "redirect:/";
    }

}
