package com.project.elearningwebapp.dao.rowmappers;

import com.project.elearningwebapp.models.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enrollment enrollment =(new BeanPropertyRowMapper<>(Enrollment.class)).mapRow(rs, rowNum);
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
        student.setUser(user);
        enrollment.setStudent(student);


        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);
        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        teacher.setUser(user);
        course.setTeacher(teacher);
        Category category = (new BeanPropertyRowMapper<>(Category.class)).mapRow(rs, rowNum);
        course.setCategory(category);
        enrollment.setCourse(course);
        return enrollment;



    }
}
