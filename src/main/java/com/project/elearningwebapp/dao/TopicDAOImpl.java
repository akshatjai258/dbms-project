package com.project.elearningwebapp.dao;


import com.project.elearningwebapp.dao.rowmappers.CategoryRowMapper;
import com.project.elearningwebapp.dao.rowmappers.TeacherRowMapper;
import com.project.elearningwebapp.dao.rowmappers.TopicRowMapper;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.models.Teacher;
import com.project.elearningwebapp.models.Topic;
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
public class TopicDAOImpl implements  TopicDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TopicDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    public TopicDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }

    public TopicDAOImpl() {
    }

    @Override
    public Topic save(Topic topic) {
        String sql = "INSERT INTO topics(topic_title, topic_lecture, topic_notes, topic_number, course_id) VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"topic_id"});
                preparedStatementUtil.setParameters(preparedStatement, topic.getTopicTitle(), topic.getTopicLecture(), topic.getTopicNotes(), topic.getTopicNumber(), topic.getCourse().getCourseId());
                return preparedStatement;

            }
        }, keyHolder);

        int topicId = keyHolder.getKey().intValue();
        topic.setTopicId(topicId);
        return topic;
    }

    @Override
    public void update(Topic topic) {
        String sql = "UPDATE topics SET topic_title=?, topic_lecture=?, topic_notes=? WHERE topic_id = ?";
        jdbcTemplate.update(sql, topic.getTopicTitle(), topic.getTopicLecture(), topic.getTopicNotes(), topic.getTopicId());
    }

    @Override
    public void delete(int topicId) {
        String sql = "DELETE FROM topics WHERE topic_id = ?";
        jdbcTemplate.update(sql, topicId);

    }

    @Override
    public List<Topic> findByCourseID(int courseId) {
        String sql = "SELECT * FROM topics NATURAL JOIN courses NATURAL JOIN teachers NATURAL JOIN users WHERE course_id =?";
        List<Topic>topics = jdbcTemplate.query(sql, new Object[]{courseId}, new TopicRowMapper());
        return topics;
    }

    @Override
    public Topic get(int topicId) {

        try {
            String sql = "SELECT * FROM topics NATURAL jOIN courses NATURAL JOIN teachers NATURAL JOIN users WHERE topic_id = ?";
            return (Topic) jdbcTemplate.queryForObject(sql, new Object[] {
                            topicId },
                    new TopicRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }




    @Override
    public int getCount(int courseId) {
        String sql = "SELECT COUNT(*) FROM topics WHERE course_id = ?";
        int x = jdbcTemplate.queryForObject(sql, new Object[]{courseId}, Integer.class);
        return x;
    }
}
