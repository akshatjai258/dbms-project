package com.project.elearningwebapp.dao.rowmappers;

import com.project.elearningwebapp.models.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Quiz quiz = (new BeanPropertyRowMapper<>(Quiz.class)).mapRow(rs, rowNum);
        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);
        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
        teacher.setUser(user);
        course.setTeacher(teacher);
        Category category = (new BeanPropertyRowMapper<>(Category.class)).mapRow(rs, rowNum);
        course.setCategory(category);

        quiz.setCourse(course);
        return quiz;
    }
}
