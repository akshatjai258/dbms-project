package com.project.elearningwebapp.dao.rowmappers;


import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.models.Topic;
import com.project.elearningwebapp.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicRowMapper implements RowMapper {
    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Topic topic = (new BeanPropertyRowMapper<>(Topic.class)).mapRow(rs, rowNum);

        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);

        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);
        teacher.setUser(user);

        course.setTeacher(teacher);
        topic.setCourse(course);
        return topic;
    }
}
