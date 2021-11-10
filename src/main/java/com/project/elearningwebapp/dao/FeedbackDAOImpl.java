package com.project.elearningwebapp.dao;


import com.project.elearningwebapp.dao.rowmappers.CourseRowMapper;
import com.project.elearningwebapp.dao.rowmappers.FeedbackRowMapper;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseDAO courseDAO;

    public FeedbackDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }

    public FeedbackDAOImpl() {
    }

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;



    @Override
    public Feedback save(Feedback feedback) {
        String sql = "INSERT INTO feedbacks (student_id, course_id, star,content,feedback_timestamp) VALUES(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"feedback_id"});
                preparedStatementUtil.setParameters(preparedStatement, feedback.getStudent().getStudentId(), feedback.getCourse().getCourseId(), feedback.getStar(), feedback.getContent(), feedback.getTimestamp());
                return preparedStatement;
            }
        }, keyHolder);
        int feedbackId = keyHolder.getKey().intValue();
        feedback.setFeedbackId(feedbackId);

        return feedback;
    }

    @Override
    public Feedback get(int FeedbackId) {
        try {
            String sql = "SELECT * FROM feedbacks  NATURAL JOIN courses NATURAL JOIN teachers NATURAL JOIN categories NATURAL JOIN users WHERE  feedback_id = ?";
            return (Feedback) jdbcTemplate.queryForObject(sql, new Object[] {
                            FeedbackId },
                    new FeedbackRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(int FeedbackId) {
        String sql = "DELETE FROM feedbacks WHERE feedback_id =?";
        jdbcTemplate.update(sql, FeedbackId);
    }

    @Override
    public void update(Feedback feedback) {
        String sql = "UPDATE feedbacks SET content = ?,star = ? WHERE feedback_id =?";
        jdbcTemplate.update(sql, feedback.getContent(), feedback.getStar(), feedback.getFeedbackId());
    }

    @Override
    public List<Feedback> getByCourseId(int courseId) {
        String sql = "SELECT * FROM feedbacks  NATURAL JOIN students NATURAL JOIN courses NATURAL JOIN users WHERE  course_id = ?";
        List<Feedback>lst = jdbcTemplate.query(sql, new Object[]{courseId}, new FeedbackRowMapper());
        return lst;
    }

    @Override
    public List<Feedback> getByStudentId(int studentId) {
        String sql = "SELECT * FROM feedbacks  NATURAL JOIN students NATURAL JOIN courses WHERE  student_id = ?";
        List<Feedback>lst = jdbcTemplate.query(sql, new Object[]{studentId}, new FeedbackRowMapper());
        return lst;
    }

    @Override
    public Boolean isFeedbackGiven(int studentId, int courseId) {
        String sql = "SELECT count(*) FROM feedbacks WHERE student_id = ? AND course_id=?";
        int x = jdbcTemplate.queryForObject(sql, new Object[]{studentId, courseId}, Integer.class);
        if(x>0){
            return true;
        }
        return  false;
    }

    @Override
    public Feedback getByStudentAndCourseId(int studentId, int courseId) {
        String sql = "SELECT * FROM feedbacks WHERE student_id =? and course_id =?";
        List<Feedback>lst = jdbcTemplate.query(sql, new Object[]{studentId, courseId}, new FeedbackRowMapper());

        if(lst.size() != 0){
            return lst.get(0);
        }
        return null;
    }

    @Override
    public Integer numberOfStars(int stars, int courseId) {
        String sql = "SELECT count(*) from feedbacks where star = ? and course_id=?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{stars, courseId}, Integer.class);
    }

    @Override
    public List<Feedback> getTopFeedbacks(int courseId) {
        String sql = "SELECT * FROM feedbacks  NATURAL JOIN students NATURAL JOIN courses NATURAL JOIN users WHERE  course_id = ? order by star DESC LIMIT 3";
        List<Feedback>lst = jdbcTemplate.query(sql, new Object[]{courseId}, new FeedbackRowMapper());
        return lst;

    }
}
