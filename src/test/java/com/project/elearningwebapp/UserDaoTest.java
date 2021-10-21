package com.project.elearningwebapp;

import com.project.elearningwebapp.dao.userDAOImpl;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.services.SecurityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserDaoTest {
    @Autowired
    private userDAOImpl dao;



    @Autowired
    private SecurityServiceImpl service;




    @BeforeEach

    void setup() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new userDAOImpl(new JdbcTemplate(dataSource));




    }

    @Test
    void TestFindAll(){

        List<User> categories=dao.findAll();
        Assertions.assertTrue(!categories.isEmpty());
    }

    @Test

    void TestSaveUser(){
        User user = new User();
        user.setUsername("jane");
        user.setPassword("jane@123");
        user.setEnabled(true);
        user.setRole("ROLE_TEACHER");
        dao.save(user);
    }

    @Test
    void TestGetById(){
        User user = dao.get(4);
        System.out.println(user.toString());
        Assertions.assertNotNull(user);
    }

    @Test
    void GetPassword(){
        String password = dao.getPassword(4);
        System.out.println(password);

    }
    @Test
    void TestGetByUserName(){
        User user = dao.findByUsername("rohan");
        System.out.println(user.toString());
        Assertions.assertNotNull(user);

    }
    @Test
    void TestActivate(){
        dao.activate(4);
    }
    @Test

    void TestChangePassword(){
        dao.changePassword(4, "password");
    }





}
