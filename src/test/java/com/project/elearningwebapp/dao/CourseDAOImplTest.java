package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class CourseDAOImplTest {
    @Autowired
    private CourseDAO dao;

    @Autowired
    private CategoryDAO cdao;

    @Autowired
    private TeacherDAO tdao;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        dao = new CourseDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        cdao = new CategoryDAOImpl(new JdbcTemplate(dataSource));
        tdao = new TeaceherDAOImpl(new JdbcTemplate(dataSource));
    }

    @Test
    void save() {
        Course course = new Course();
        Teacher t = tdao.get(4);
        Category cat = cdao.getById(24);
        course.setTeacher(t);
        course.setCategory(cat);
        course.setCourseName("Master Web Development");
        course.setTimestamp(new Timestamp(System.currentTimeMillis()));

        Course savedCourse = dao.save(course);
        System.out.println(savedCourse);

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
        Course course = dao.get(5);
        System.out.println(course);
    }

    @Test
    void findAll() {
//        List<Course> courses = dao.findAll();
//        System.out.println(courses);
    }

    @Test
    void sortByRating() {
        List<Course>courses=dao.sortByRating();
        System.out.println(courses);
    }


    @Test
    void sortByEnrolments() {
        PageRequest pageable = PageRequest.of(0, 6, Sort.Direction.fromString("DESC"), "avg_rating");
//        Page<Course>course = dao.advanceFilter(null, "ANY", "ANY",pageable);
//        System.out.println(course.toList());
    }

    @Test
    void sortByDate() {
        List<Course>courses=dao.sortByDate();
        System.out.println(courses);
    }

    @Test
    void getAllByTeacherId() {

    }

    @Test
    void getAllByCategoryId() {
        List<Course>courses = dao.getAllByCategoryId(24);
        System.out.println(courses);
    }

    @Test
    void searchedCourses() {
        String text = "Master";
        List<Course>courses = dao.searchedCourses(text);
        System.out.println(courses);
    }

    @Test
    void getAllByDifficulty() {

    }
}