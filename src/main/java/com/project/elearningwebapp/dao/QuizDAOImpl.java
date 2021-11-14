package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.QuestionRowMapper;
import com.project.elearningwebapp.dao.rowmappers.QuizRowMapper;
import com.project.elearningwebapp.models.Question;
import com.project.elearningwebapp.models.Quiz;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class QuizDAOImpl implements QuizDAO{

    public QuizDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }


    public QuizDAOImpl() {
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;


    @Override
    public Quiz save(Quiz quiz) {
        String sql = "INSERT INTO quizzes (quiz_title, quiz_description, quiz_instructions, week, course_id, quiz_time) VALUES(?,?,?,?,?,?)";


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"quiz_id"});
                preparedStatementUtil.setParameters(preparedStatement, quiz.getQuizTitle(), quiz.getQuizDescription(), quiz.getQuizInstructions(), quiz.getWeek(), quiz.getCourse().getCourseId(), quiz.getQuizTime());
                return preparedStatement;
            }
        }, keyHolder);
        int quizId = keyHolder.getKey().intValue();
        quiz.setQuizId(quizId);
        return quiz;
    }

    @Override
    public void update(Quiz quiz) {
        String sql = "UPDATE quizzes SET quiz_title = ?, quiz_description = ?, quiz_instructions=?, quiz_time=? WHERE quiz_id =?";
        jdbcTemplate.update(sql, quiz.getQuizTitle(), quiz.getQuizDescription(), quiz.getQuizInstructions(), quiz.getQuizTime(), quiz.getQuizId());
    }

    @Override
    public void delete(Integer quizId) {
        String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quizId);
    }

    @Override
    public Quiz get(int QuizId) {
        try {
            String sql = "SELECT * FROM quizzes NATURAL JOIN courses NATURAL JOIN teachers NATURAL JOIN users NATURAL JOIN categories WHERE quiz_id = ?";
            return (Quiz) jdbcTemplate.queryForObject(sql, new Object[]{
                            QuizId},
                    new QuizRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Quiz> findAllByCourseId(int courseId) {
        String sql = "SELECT * FROM quizzes NATURAL JOIN courses NATURAL JOIN teachers NATURAL JOIN users NATURAL JOIN categories WHERE course_id = ?";
        return jdbcTemplate.query(sql, new Object[]{courseId}, new QuizRowMapper());
    }
}
