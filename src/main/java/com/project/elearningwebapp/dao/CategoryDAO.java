package com.project.elearningwebapp.dao;


import com.project.elearningwebapp.models.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryDAO {

    public Category save(Category category);

    public List<Category>findAll();

    public Category getById(int id);

    public void update(Category category, int id);

    public void delete(int id);
}