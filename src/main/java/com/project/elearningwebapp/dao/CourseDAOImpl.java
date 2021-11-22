package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.CourseRowMapper;
import com.project.elearningwebapp.dao.rowmappers.StudentRowMapper;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CourseDAOImpl() {
    }

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CourseDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }

    @Override
    public Course save(Course course) {
        String sql = "INSERT INTO courses (teacher_id, course_name, course_description,course_overview, course_price, avg_rating, timestamp, category_id, course_thumbnail, course_difficulty, no_of_weeks) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
//        jdbcTemplate.update(sql, course.getTeacher().getTeacherId(), course.getCourseName(), course.getCourseDescription(),
//                course.getCoursePrice(), course.getAvgRating(), course.getTimestamp(), course.getCategory().getCategoryId(), course.getCourseThumbnail(), course.getCourseDifficulty());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"course_id"});
                preparedStatementUtil.setParameters(preparedStatement, course.getTeacher().getTeacherId(), course.getCourseName(), course.getCourseDescription(), course.getCourseOverview(),
                        course.getCoursePrice(), course.getAvgRating(), course.getTimestamp(), course.getCategory().getCategoryId(), course.getCourseThumbnail(), course.getCourseDifficulty(), course.getNoOfWeeks());
                return preparedStatement;
            }
        }, keyHolder);
        int courseId = keyHolder.getKey().intValue();
        course.setCourseId(courseId);

        return course;
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET course_name = ?, course_description = ?, course_overview=?, course_thumbnail = ?, course_price=?, course_difficulty=?, no_of_weeks=?, category_id=? WHERE course_id =?";
        jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDescription(), course.getCourseOverview(), course.getCourseThumbnail(), course.getCoursePrice(), course.getCourseDifficulty(), course.getNoOfWeeks(), course.getCategory().getCategoryId(), course.getCourseId());


    }


    @Override
    public void delete(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id =?";
        jdbcTemplate.update(sql, courseId);
    }

    @Override
    public Course get(int courseId) {
        try {
            String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users WHERE course_id = ?";
            return (Course) jdbcTemplate.queryForObject(sql, new Object[]{
                            courseId},
                    new CourseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM courses", Integer.class);
    }

    public int count_new(int teacherId) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM courses where teacher_id=?", new Object[]{teacherId}, Integer.class);
    }

    @Override
    public int numOfLectures(int teacherId) {

        String sql = "SELECT count(*) from courses NATURAL JOIN topics where courses.teacher_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{teacherId}, Integer.class);
    }

    @Override
    public void updateRating(int courseId, double avgRating) {
        String sql = "UPDATE courses SET avg_rating = ? WHERE course_id =?";
        jdbcTemplate.update(sql, avgRating, courseId);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("course_id");
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users ORDER BY " + order.getProperty() + " "
                + order.getDirection().name()
                + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());
        return new PageImpl<Course>(courses, pageable, count());
    }

    @Override
    public List<Course> sortByRating() {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users ORDER BY avg_rating DESC";
        List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());
        return courses;
    }

    @Override
    public List<Course> sortByEnrolments() {
        return null;
    }

    @Override
    public List<Course> sortByDate() {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users ORDER BY timestamp DESC";
        List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());
        return courses;
    }

    @Override
    public Page<Course> getAllByTeacherId(int teacherId, Pageable pageable) {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users where teacher_id = ?";
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("course_id");
        sql += " ORDER BY " + order.getProperty() + " "
                + order.getDirection().name()
                + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        List<Course> courses = jdbcTemplate.query(sql, new Object[]{teacherId}, new CourseRowMapper());
        return new PageImpl<Course>(courses, pageable, count_new(teacherId));
    }

    @Override
    public List<Course> getAllByCategoryId(int categoryId) {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users where category_id = ?";
        List<Course> courses = jdbcTemplate.query(sql, new Object[]{categoryId}, new CourseRowMapper());
        return courses;
    }

    @Override
    public List<Course> searchedCourses(String text) {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users where MATCH (course_name, course_description) Against(?)";
        List<Course> courses = jdbcTemplate.query(sql, new Object[]{text}, new CourseRowMapper());
        return courses;
    }

    @Override
    public List<Course> getAllByDifficulty(String difficulty) {
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users WHERE course_difficulty = ?";
        List<Course> courses = jdbcTemplate.query(sql, new Object[]{difficulty}, new CourseRowMapper());
        return courses;
    }

    @Override
    public Page<Course> advanceFilter(String course_text, Integer category_id, String difficulty_text, Pageable pageable) {


        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("course_id");
        String sql = "SELECT * FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users ";
        if (!course_text.equals("")) {

            sql += "WHERE Match (course_name, course_description) AGAINST(" + "'" + course_text + "') AND";
        }

        if (category_id != 0) {
            if (!sql.substring(sql.length() - 3).equals("AND")) {
                sql += " WHERE ";
            }

            sql += " categories.category_id = " + "'" + category_id + "' AND";
        }

        if (!difficulty_text.equals("Any")) {
            if (!sql.substring(sql.length() - 3).equals("AND")) {
                sql += " WHERE ";
            }

            sql += " course_difficulty = " + "'" + difficulty_text + "'";
        }

        if (sql.substring(sql.length() - 3).equals("AND")) {
            sql = sql.substring(0, sql.length() - 3);
        }
        sql += " ORDER BY " + order.getProperty() + " "
                + order.getDirection().name()
                + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();


        List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());
        return new PageImpl<Course>(courses, pageable, count());
    }

    @Override
    public Integer advanceCount(String course_text, Integer category_id, String difficulty_text) {
        String sql = "SELECT count(*) FROM courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users ";
        if (!course_text.equals("")) {

            sql += "WHERE Match (course_name, course_description) AGAINST(" + "'" + course_text + "') AND";
        }

        if (category_id != 0) {
            if (!sql.substring(sql.length() - 3).equals("AND")) {
                sql += " WHERE ";
            }

            sql += " categories.category_id = " + "'" + category_id + "' AND";
        }

        if (!difficulty_text.equals("Any")) {
            if (!sql.substring(sql.length() - 3).equals("AND")) {
                sql += " WHERE ";
            }

            sql += " course_difficulty = " + "'" + difficulty_text + "'";
        }

        if (sql.substring(sql.length() - 3).equals("AND")) {
            sql = sql.substring(0, sql.length() - 3);
        }

        return jdbcTemplate.queryForObject(sql, Integer.class);

    }

}
