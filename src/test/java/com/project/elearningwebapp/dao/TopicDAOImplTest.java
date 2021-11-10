package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Topic;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        System.out.println(dao.getCount(4, 1));
    }

    @Test
    void getCount(){
        // get the list of topics belonging to a certain course

        List<Topic>topics = dao.findByCourseID(1);
        ArrayList<ArrayList<Topic>> ans = new ArrayList<ArrayList<Topic>>(10);
        for(int i=1;i<=10;i++){
            ans.add(new ArrayList<>());
        }
        for (Topic t:topics) {
            ArrayList<Topic>current = ans.get(t.getWeek());
            current.add(t);
            ans.set(t.getWeek(), current);
        }


        for(int i=1;i<=10;i++){
            System.out.println(ans.get(i-1));
        }
        for(int i=1;i<=10;i++){
            Collections.sort(ans.get(i-1),new Comparator<Topic>() {
                @Override
                public int compare(Topic s1, Topic s2) {
                    return Integer.compare(s1.getTopicNumber(), s2.getTopicNumber());
                }
            });
        }
        for(int i=1;i<=10;i++){
            System.out.println(ans.get(i-1));
        }
    }
}