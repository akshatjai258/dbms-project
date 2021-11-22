package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.StudentRowMapper;
import com.project.elearningwebapp.models.Student;
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
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    public StudentDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }

    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO students (gender, date_of_birth, country, state, city, street, pincode, house_no, user_id) VALUES(?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"student_id"});
                preparedStatementUtil.setParameters(preparedStatement, student.getGender(), student.getDateOfBirth(),
                        student.getCountry(), student.getState(), student.getCity(), student.getStreet(), student.getPincode(),
                        student.getHouseNo(), student.getUser().getUser_id());
                return preparedStatement;
            }
        }, keyHolder);
        int studentId = keyHolder.getKey().intValue();
        student.setStudentId(studentId);
        return student;
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM students NATURAL JOIN users";
        List<Student> students = jdbcTemplate.query(sql, new StudentRowMapper());
        return students;
    }

    @Override
    public List<Student> getAllByCourseId(String courseId) {
        return null;
    }

    @Override
    public Student get(int studentId) {
        try {
            String sql = "SELECT * FROM students NATURAL JOIN users WHERE student_id = ?";
            return (Student) jdbcTemplate.queryForObject(sql, new Object[] {
                            studentId },
                    new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Student getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM students NATURAL JOIN users WHERE user_id = ?";
            return (Student) jdbcTemplate.queryForObject(sql, new Object[] { userId }, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getStudentIdByUserId(int userId) {
        try {
            String sql = "SELECT student_id FROM students WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET gender = ?, date_of_birth = ?, house_no = ?,country = ?, street = ?, city = ?, state = ?, "
                + "pincode = ?, profile_pic = ?, no_of_photos_uploaded =? WHERE student_id = ?";

        jdbcTemplate.update(sql, student.getGender(), student.getDateOfBirth(), student.getHouseNo(),student.getCountry(),
                student.getStreet(), student.getCity(), student.getState(),
                student.getPincode(), student.getProfilePic(), student.getNoOfPhotosUploaded(), student.getStudentId());
    }

    @Override
    public void delete(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        jdbcTemplate.update(sql, studentId);
    }
}
