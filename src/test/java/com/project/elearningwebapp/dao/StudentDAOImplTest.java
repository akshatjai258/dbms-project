package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

class StudentDAOImplTest {

    @Autowired
    private StudentDAO dao;

    @Autowired
    private userDAO udao;


    @BeforeEach

    void setup() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new StudentDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        udao = new userDAOImpl(new JdbcTemplate(dataSource));
    }

    @Test
    void save() {
        Student st = new Student();
        User u = udao.get(1);

        st.setUser(u);
        st.setCity("chanderi");
        st.setCountry("India");
        st.setGender("Male");
        st.setDateOfBirth(null);
        st.setPincode("473446");
        st.setHouseNo("15-A");
        st.setState("Madhya Pradesh");
        st.setStreet("Lane Lost");

        dao.save(st);


    }

    @Test
    void getAll() {
        List<Student>students = dao.getAll();
        System.out.println(students.get(0).getStudentId());
    }

    @Test
    void getAllByCourseId() {
    }

    @Test
    void get() {
        Student st = dao.get(1);
        System.out.println(st.getUser().getUsername());
    }

    @Test
    void getByUserId() {
        Student st = dao.getByUserId(17);
        System.out.println(st.getUser().getUsername());
    }

    @Test
    void getStudentIdByUserId() {
        int st = dao.getStudentIdByUserId(17);
        System.out.println(st);
    }

    @Test
    void update() {
        Student student = dao.get(3);
        student.setCity("Mungaowli");
        dao.update(student);

    }

    @Test
    void delete() {
        dao.delete(3);
    }
}