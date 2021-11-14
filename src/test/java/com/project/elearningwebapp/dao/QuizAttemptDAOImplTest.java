package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.QuizAttempt;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class QuizAttemptDAOImplTest {

    @Autowired
    private QuizAttemptDAO quizAttemptDAO;

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;


    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        quizAttemptDAO = new QuizAttemptDAOImpl(new JdbcTemplate(dataSource));
        quizDAO = new QuizDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        studentDAO = new StudentDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
    }

    @Test
    void save() {
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuiz(quizDAO.get(2));
        quizAttempt.setStudent(studentDAO.get(38));


        quizAttemptDAO.save(quizAttempt);
    }

    @Test
    void getByStudentId() {
        System.out.println(quizAttemptDAO.getByStudentId(38));
    }

    @Test
    void getByQuizId() {
        System.out.println(quizAttemptDAO.getByQuizId(2));
    }
}