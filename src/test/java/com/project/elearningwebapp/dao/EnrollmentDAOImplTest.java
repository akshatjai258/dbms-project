package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Enrollment;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class EnrollmentDAOImplTest {

    @Autowired
    private EnrollmentDAO dao;

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

        dao = new EnrollmentDAOImpl(new JdbcTemplate(dataSource));
        courseDAO = new CourseDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        studentDAO = new StudentDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());


    }

    @Test
    void enroll() {
        Enrollment enrollment = new Enrollment();

        Student student = studentDAO.get(38);
        Course course = courseDAO.get(5);
        System.out.println(student);
        System.out.println(course);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollment_time(new Timestamp(System.currentTimeMillis()));
        dao.enroll(enrollment);

    }

    @Test
    void unEnroll() {
        dao.unEnroll(38, 1);
    }

    @Test
    void isEnrolled() {
        dao.coursesEnrolled(38);
    }
}