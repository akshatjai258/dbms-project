package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Quiz;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizDAOImplTest {

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private CourseDAO courseDAO;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        quizDAO = new QuizDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        courseDAO = new CourseDAOImpl(new JdbcTemplate(dataSource));
    }

    @Test
    void save() {
        Quiz quiz = new Quiz();
        quiz.setQuizTitle("new Test");
        quiz.setCourse(courseDAO.get(1));
        quiz.setQuizDescription("new description");
        quiz.setQuizTime(130);
        quiz.setQuizInstructions("new instructions");
        quiz.setWeek(1);

        quizDAO.save(quiz);
    }

    @Test
    void update() {
        Quiz quiz = quizDAO.get(1);
        quiz.setQuizTitle("testing..");
        quizDAO.update(quiz);
    }

    @Test
    void delete() {
        quizDAO.delete(1);
    }

    @Test
    void get() {


    }

    @Test
    void findAllByCourseId() {
        List<Quiz>lst = quizDAO.findAllByCourseId(1);
        System.out.println(lst);
    }
}