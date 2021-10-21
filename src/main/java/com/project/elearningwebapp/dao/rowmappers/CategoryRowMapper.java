package com.project.elearningwebapp.dao.rowmappers;

import com.project.elearningwebapp.models.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = (new BeanPropertyRowMapper<>(Category.class)).mapRow(rs, rowNum);
        return category;
    }
}
