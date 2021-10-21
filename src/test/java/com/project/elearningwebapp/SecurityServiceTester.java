package com.project.elearningwebapp;


import com.project.elearningwebapp.dao.userDAOImpl;
import com.project.elearningwebapp.services.SecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SecurityServiceTester {
    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private userDAOImpl dao;


    @BeforeEach

    void setup() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");
        dao = new userDAOImpl(new JdbcTemplate(dataSource));


    }
    @Test
    private void findLogin(){
        dao.findAll();
//        User user = securityService.findLoggedInUser();
//        Assertions.assertNotNull(user);
    }
}
