package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.QuestionRowMapper;
import com.project.elearningwebapp.models.Question;
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
public class QuestionDAOImpl implements QuestionDAO{
    public QuestionDAOImpl() {
    }

    public QuestionDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Question save(Question question) {
        String sql = "INSERT INTO questions (question_text, option_one, option_two, option_three, option_four, answer, quiz_id) VALUES(?,?,?,?,?,?,?)";


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"question_id"});
                preparedStatementUtil.setParameters(preparedStatement, question.getQuestionText(), question.getOptionOne(), question.getOptionTwo(),question.getOptionThree(),
                        question.getOptionFour(), question.getAnswer(), question.getQuiz().getQuizId());

                return preparedStatement;
            }
        }, keyHolder);
        int questionId = keyHolder.getKey().intValue();
        question.setQuestionId(questionId);
        return question;
    }

    @Override
    public void delete(Integer questionId) {
        String sql = "DELETE FROM questions WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }

    @Override
    public void update(Question question) {
        String sql = "UPDATE questions SET question_text = ?, option_one = ?, option_two=?, option_three=?, option_four=?, answer=? WHERE question_id =?";
        jdbcTemplate.update(sql, question.getQuestionText(), question.getOptionOne(), question.getOptionTwo(), question.getOptionThree(), question.getOptionFour(), question.getAnswer(), question.getQuestionId());
    }

    @Override
    public Question get(int questionId) {
        try {
            String sql = "SELECT * FROM questions NATURAL JOIN quizzes NATURAL JOIN courses NATURAL JOIN teachers NATURAL JOIN users NATURAL JOIN categories WHERE question_id = ?";
            return (Question) jdbcTemplate.queryForObject(sql, new Object[]{
                            questionId},
                    new QuestionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Question> findByQuizId(int quizId) {
        String sql = "SELECT * FROM questions NATURAL JOIN quizzes NATURAL JOIN courses Natural JOIN teachers NATURAL JOIN users NATURAL JOIN categories WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new Object[]{quizId}, new QuestionRowMapper());
    }
}
