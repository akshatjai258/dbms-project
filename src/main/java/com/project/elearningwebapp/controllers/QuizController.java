package com.project.elearningwebapp.controllers;


import com.project.elearningwebapp.dao.*;
import com.project.elearningwebapp.models.*;
import com.project.elearningwebapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {


    @Autowired
    private SecurityService securityService;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private QuizAttemptDAO quizAttemptDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    @GetMapping("/quiz/add/{courseId}")
    public String addQuizForm(Model model, @PathVariable Integer courseId, @AuthenticationPrincipal MyUserDetails loggedUser){

        // Requirement user must be author

        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }
        model.addAttribute("securityservice", securityService);
        Quiz quiz = new Quiz();
        Course course = courseDAO.get(courseId);

        if(course.getTeacher().getUser().getUser_id() != loggedUser.getUser().getUser_id()){
            return "redirect:/";
        }
        quiz.setCourse(course);
        model.addAttribute("course", course);
        model.addAttribute("quiz", quiz);

        return "addQuiz";
    }

    @PostMapping("/quiz/save/{courseId}")
    public String saveQuiz(@ModelAttribute("quiz") Quiz quiz, @PathVariable Integer courseId){
        System.out.println(quiz);
        quiz.setCourse(courseDAO.get(courseId));
        Quiz saveedQuiz = quizDAO.save(quiz);
        return "redirect:/teacher/view-quiz/" + saveedQuiz.getQuizId();
    }

    @GetMapping("/quiz/add-question/{quizId}")
    public String addQuestion(Model model, @PathVariable Integer quizId, @AuthenticationPrincipal MyUserDetails loggedUser){

        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }



        Quiz quiz = quizDAO.get(quizId);
        model.addAttribute("quiz", quiz);

        Course course = courseDAO.get(quiz.getCourse().getCourseId());
        if(course.getTeacher().getUser().getUser_id() != loggedUser.getUser().getUser_id()){
            return "redirect:/";
        }

        model.addAttribute("securityservice", securityService);
        Question question = new Question();
        model.addAttribute("question", question);

        return "addQuestion";
    }

    @PostMapping("/quiz/save-question/{quizId}")
    public String saveQuestion(@ModelAttribute("question") Question question, @PathVariable Integer quizId){
        Quiz quiz = quizDAO.get(quizId);
        question.setQuiz(quiz);
        System.out.println(question);
        questionDAO.save(question);

        return "redirect:/teacher/view-quiz/" + quizId;
    }

    @GetMapping("/quiz/edit-question/{questionId}")
    public String editQuestion(Model model, @PathVariable Integer questionId, @AuthenticationPrincipal MyUserDetails loggedUser){

        if(loggedUser == null || loggedUser.getUser().getRole().equals("ROLE_STUDENT")){
            return "redirect:/";
        }
        Question question = questionDAO.get(questionId);

        Course course = courseDAO.get(question.getQuiz().getCourse().getCourseId());

        if(course.getTeacher().getUser().getUser_id() != loggedUser.getUser().getUser_id()){
            return "redirect:/";
        }
        model.addAttribute("question", question);
        model.addAttribute("securityservice", securityService);
        Quiz quiz = question.getQuiz();
        model.addAttribute("quiz", quiz);
        return "editQuestion";

    }

    @GetMapping("/teacher/view-quiz/{quizId}")
    public String viewQuiz(@PathVariable Integer quizId, Model model, @AuthenticationPrincipal MyUserDetails loggedUser){
        model.addAttribute("securityservice", securityService);
        Quiz quiz = quizDAO.get(quizId);

        Course course = courseDAO.get(quiz.getCourse().getCourseId());
        if(course.getTeacher().getUser().getUser_id() != loggedUser.getUser().getUser_id()){
            return "redirect:/";
        }

        model.addAttribute("quiz", quiz);
        List<Question> questionList = questionDAO.findByQuizId(quizId);
        model.addAttribute("questionList", questionList);
        return "viewQuiz";
    }

    @PostMapping ("/quiz/update-question/{questionId}")
    public String updateQuestion(@ModelAttribute("question") Question question, @PathVariable Integer questionId){
        System.out.println(question);
        question.setQuestionId(questionId);
        questionDAO.update(question);
        return "redirect:/";

    }

    @GetMapping("/quiz/attempt/{quizId}")
    public String attemptQuiz(Model model, @PathVariable Integer quizId, @AuthenticationPrincipal MyUserDetails loggedUser){
        // Requirement : student must be enrolled in the course to give the qui

        if(loggedUser.getUser().getRole().equals("ROLE_TEACHER")){
            return "redirect:/";
        }

        Quiz quiz = quizDAO.get(quizId);
        if(!enrollmentDAO.isEnrolled(studentDAO.getStudentIdByUserId(loggedUser.getUser().getUser_id()), courseDAO.get(quiz.getCourse().getCourseId()).getCourseId())){
            return "redirect:/";
        }

        model.addAttribute("securityservice", securityService);

        model.addAttribute("quiz", quiz);
        List<Question> questionList = questionDAO.findByQuizId(quizId);
        model.addAttribute("questionList", questionList);

        List<String>arrayList = new ArrayList<String>();
        for(Question q:questionList){
            arrayList.add("");
        }
        FormBean formBean = new FormBean(arrayList);
        model.addAttribute("formBean", formBean);

        return "attemptQuiz";
    }

    @PostMapping("/quiz/submit/{quizId}")
    public String submitQuiz(@ModelAttribute("formBean") FormBean formBean, @PathVariable("quizId") Integer quizId, @AuthenticationPrincipal MyUserDetails loggedUser){
        System.out.println(formBean);
        List<Question>questionList = questionDAO.findByQuizId(quizId);
        int i=0, marks=0;
        for(Question q:questionList){
            if(q.getAnswer().equals(formBean.getResponses().get(i))){
                marks++;
            }
            i++;
        }

        int total=questionList.size();
        QuizAttempt quizAttempt = new QuizAttempt();


        quizAttempt.setStudent(studentDAO.getByUserId(loggedUser.getUser().getUser_id()));
        quizAttempt.setQuiz(quizDAO.get(quizId));
        quizAttempt.setMarksGot(marks);
        quizAttempt.setTotalMarks(total);

        quizAttemptDAO.save(quizAttempt);
        return "redirect:/student/view-report/" + quizId;
    }

    @GetMapping("/delete/{questionId}")
    public String DeleteQuestion(@PathVariable Integer questionId){
        Question question = questionDAO.get(questionId);

        Quiz quiz = quizDAO.get(question.getQuiz().getQuizId());

        questionDAO.delete(questionId);
        return "redirect:/teacher/view-quiz/"+quiz.getQuizId();
    }

    @GetMapping("/student/view-report/{quizId}")
    public String viewReport(Model model, @PathVariable("quizId") Integer quizId, @AuthenticationPrincipal MyUserDetails loggedUser){
        model.addAttribute("securityservice", securityService);
        Student student = studentDAO.getByUserId(loggedUser.getUser().getUser_id());
        Quiz quiz = quizDAO.get(quizId);
        QuizAttempt quizAttempt = quizAttemptDAO.getByStudentAndQuizID(student.getStudentId(), quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("student", student);
        model.addAttribute("quizAttempt", quizAttempt);
        List<Question> questionList = questionDAO.findByQuizId(quizId);
        model.addAttribute("questionList", questionList);

        return "viewReport";
    }

}
