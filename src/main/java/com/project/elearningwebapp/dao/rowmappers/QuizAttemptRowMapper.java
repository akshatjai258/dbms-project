package com.project.elearningwebapp.dao.rowmappers;

import com.project.elearningwebapp.models.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizAttemptRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        QuizAttempt quizAttempt = (new BeanPropertyRowMapper<>(QuizAttempt.class)).mapRow(rs, rowNum);
        Quiz quiz = (new BeanPropertyRowMapper<>(Quiz.class)).mapRow(rs, rowNum);
        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);
        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
        teacher.setUser(user);
        course.setTeacher(teacher);
        Category category = (new BeanPropertyRowMapper<>(Category.class)).mapRow(rs, rowNum);
        course.setCategory(category);


        quiz.setCourse(course);

        quizAttempt.setQuiz(quiz);

        Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
        student.setUser(user);
        quizAttempt.setQuiz(quiz);
        return quizAttempt;
    }
}
