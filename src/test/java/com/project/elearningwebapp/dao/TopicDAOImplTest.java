package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.models.Topic;
import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class TopicDAOImplTest {

    @Autowired
    private TopicDAO dao;

    @Autowired
    private CourseDAO cdao;



    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new TopicDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        cdao = new CourseDAOImpl(new JdbcTemplate(dataSource));

    }

    @Test
    void testSave() {
        Topic t = new Topic();
        Course course = cdao.get(4);
        t.setCourse(course);
        t.setTopicTitle("Introduction");
        t.setTopicNumber(1);
        t.setTopicLecture("Introduction.mp4");
        t.setTopicNotes("Introduction.pdf");
        dao.save(t);

    }

    @Test
    void testUpdate() {
        Topic t = dao.get(1);
        t.setTopicLecture("introduction_revised.mp4");
        dao.update(t);
    }

    @Test
    void testDelete() {
        dao.delete(1);
    }

    @Test
    void testFindByCourseID() {
        List<Topic>topics = dao.findByCourseID(4);
        System.out.println(topics);
    }

    @Test
    void testGet() {
        Topic topic = dao.get(2);
        System.out.println(topic);
    }

    @Test
    void countLectures() {
        System.out.println(dao.getCount(4));
    }

    @Test
    void getCount(){

    }
}