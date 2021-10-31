package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.UserDaoTest;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
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
class TeaceherDAOImplTest {

    @Autowired
    private TeacherDAO dao;

    @Autowired
    private userDAO udao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new TeaceherDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        udao = new userDAOImpl(new JdbcTemplate(dataSource));
    }

    @Test
    void save() {
        Teacher t = new Teacher();
        User u = udao.get(62);
        t.setUser(u);
        t.setGender("Male");
        dao.save(t);

    }

    @Test
    void update() {
        Teacher t=dao.get(2);
        t.setGender("female");
        dao.update(t);
    }

    @Test
    void delete() {
        Teacher t = dao.get(2);
        dao.delete(t.getTeacherId());
    }

    @Test
    void getAll() {
        List<Teacher> lst = dao.getAll();
        System.out.println(lst);

    }

    @Test
    void get() {
        Teacher teacher = dao.get(3);
        System.out.println(teacher);
    }

    @Test
    void getByUserId() {
        Teacher teacher = dao.getByUserId(62);
    }

    @Test
    void getTeacherIdByUserId() {
        int  x= dao.getTeacherIdByUserId(62);
        System.out.println(x);
    }
}