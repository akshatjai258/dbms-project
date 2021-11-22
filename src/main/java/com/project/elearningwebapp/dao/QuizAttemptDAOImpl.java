package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.QuizAttemptRowMapper;
import com.project.elearningwebapp.models.QuizAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizAttemptDAOImpl implements QuizAttemptDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public QuizAttemptDAOImpl() {
    }

    public QuizAttemptDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(QuizAttempt quizAttempt) {
        String sql = "INSERT INTO quiz_attempts (student_id, quiz_id, marks_got, total_marks) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, quizAttempt.getStudent().getStudentId(), quizAttempt.getQuiz().getQuizId(), quizAttempt.getMarksGot(), quizAttempt.getTotalMarks());

    }

    @Override
    public List<QuizAttempt> getByStudentId(int studentId) {

        String sql = "SELECT * FROM quiz_attempts Natural JOIN quizzes NATURAL JOIN students NATURAL JOIN users where student_id = ?";
        List<QuizAttempt>attempts = jdbcTemplate.query(sql, new Object[]{studentId}, new QuizAttemptRowMapper());
        return attempts;
    }

    @Override
    public List<QuizAttempt> getByQuizId(int quizId) {
        String sql = "SELECT * FROM quiz_attempts Natural JOIN quizzes NATURAL JOIN students NATURAL JOIN users where quiz_id = ?";
        List<QuizAttempt>attempts = jdbcTemplate.query(sql, new Object[]{quizId}, new QuizAttemptRowMapper());
        return attempts;
    }

    @Override
    public QuizAttempt getByStudentAndQuizID(int studentId, int quizId) {
        String sql1 = "SELECT count(*) FROM quiz_attempts Natural JOIN quizzes NATURAL JOIN students NATURAL JOIN users where quiz_id = ? AND student_id=?";
        if(jdbcTemplate.queryForObject(sql1, new Object[]{quizId, studentId}, Integer.class) == 0){
            return null;
        }

        String sql = "SELECT * FROM quiz_attempts Natural JOIN quizzes NATURAL JOIN students NATURAL JOIN users where quiz_id = ? AND student_id=?";


        QuizAttempt attempt = (QuizAttempt) jdbcTemplate.queryForObject(sql, new Object[]{quizId, studentId}, new QuizAttemptRowMapper());
        return attempt;

    }
}
