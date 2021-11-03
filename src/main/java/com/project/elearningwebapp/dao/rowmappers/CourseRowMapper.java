package com.project.elearningwebapp.dao.rowmappers;


import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);
        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
        teacher.setUser(user);
        course.setTeacher(teacher);
        Category category = (new BeanPropertyRowMapper<>(Category.class)).mapRow(rs, rowNum);
        course.setCategory(category);

        return course;
    }
}
