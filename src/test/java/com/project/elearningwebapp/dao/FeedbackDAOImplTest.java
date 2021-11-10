package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Feedback;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackDAOImplTest {

    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");
        feedbackDAO = new FeedbackDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        studentDAO = new StudentDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        courseDAO = new CourseDAOImpl(new JdbcTemplate(dataSource));

    }

    @Test
    void save() {
        Feedback feedback = new Feedback();
        feedback.setCourse(courseDAO.get(1));
        feedback.setStudent(studentDAO.get(38));
        feedback.setContent("hello");
        feedback.setStar(2);
        feedback.setTimestamp(new Timestamp(System.currentTimeMillis()));

        feedbackDAO.save(feedback);
    }

    @Test
    void get() {
        System.out.println(feedbackDAO.get(1));
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getByCourseId() {
        System.out.println(feedbackDAO.getByCourseId(1));
    }

    @Test
    void getByStudentId() {
    }
}