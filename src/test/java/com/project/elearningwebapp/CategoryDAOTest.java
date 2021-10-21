package com.project.elearningwebapp;

import com.project.elearningwebapp.dao.CategoryDAO;
import com.project.elearningwebapp.dao.CategoryDAOImpl;
import com.project.elearningwebapp.models.Category;
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
public class CategoryDAOTest {
    @Autowired
    private CategoryDAOImpl dao;


    @BeforeEach

    void setup() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new CategoryDAOImpl(new JdbcTemplate(dataSource));
    }

    @Test
    void TestFindAll(){

        List<Category> categories=dao.findAll();
        Assertions.assertTrue(categories.isEmpty());
    }

    @Test
    void TestSave(){
        Category category = new Category();
        category.setCategoryName("Web Designing");
        dao.save(category);

    }

    @Test
    void TestGetById(){
        int id = 6;
        Category category = dao.getById(6);
        Assertions.assertNotNull(category);
    }

    @Test
    void TestUpdate(){
        int id = 6;
        Category category = new Category();
        category.setCategoryName("Web Development");
        dao.update(category, id);
    }

    @Test
    void TestDelete(){
        int id = 6;
        dao.delete(6);
    }

}
