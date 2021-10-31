package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.StudentRowMapper;
import com.project.elearningwebapp.dao.rowmappers.TeacherRowMapper;
import com.project.elearningwebapp.models.Student;
import com.project.elearningwebapp.models.Teacher;
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
public class TeaceherDAOImpl implements TeacherDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TeaceherDAOImpl(JdbcTemplate jdbcTemplate, PreparedStatementUtil preparedStatementUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.preparedStatementUtil = preparedStatementUtil;
    }



    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Teacher save(Teacher teacher) {
        String sql = "INSERT INTO teachers (gender, country, state, city, street, pincode, house_no, user_id, profile_pic, about_me) VALUES(?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"teacher_id"});
                preparedStatementUtil.setParameters(preparedStatement, teacher.getGender(),
                        teacher.getCountry(), teacher.getState(), teacher.getCity(), teacher.getStreet(), teacher.getPincode(),
                        teacher.getHouseNo(), teacher.getUser().getUser_id(), teacher.getProfilePic(), teacher.getAboutMe());
                return preparedStatement;
            }
        }, keyHolder);
        int teacherId = keyHolder.getKey().intValue();
        teacher.setTeacherId(teacherId);
        return teacher;
    }

    @Override
    public void update(Teacher teacher) {
        String sql = "UPDATE teachers SET gender = ?, house_no = ?,country = ?, street = ?, city = ?, state = ?, "
                + "pincode = ?, profile_pic = ?, no_of_photos_uploaded =?, about_me=? WHERE teacher_id = ?";

        jdbcTemplate.update(sql, teacher.getGender(), teacher.getHouseNo(),teacher.getCountry(),
                teacher.getStreet(), teacher.getCity(), teacher.getState(),
                teacher.getPincode(), teacher.getProfilePic(), teacher.getNoOfPhotosUploaded(),teacher.getAboutMe(), teacher.getTeacherId());
    }

    @Override
    public void delete(int TeacherId) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";
        jdbcTemplate.update(sql, TeacherId);
    }

    @Override
    public List<Teacher> getAll() {
        String sql = "SELECT * FROM teachers NATURAL JOIN users";
        List<Teacher> teachers = jdbcTemplate.query(sql, new TeacherRowMapper());
        return teachers;
    }

    @Override
    public Teacher get(int TeacherId) {
        try {
            String sql = "SELECT * FROM teachers NATURAL JOIN users WHERE teacher_id = ?";
            return (Teacher) jdbcTemplate.queryForObject(sql, new Object[] {
                            TeacherId },
                    new TeacherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Teacher getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM teachers NATURAL JOIN users WHERE user_id = ?";
            return (Teacher) jdbcTemplate.queryForObject(sql, new Object[] { userId }, new TeacherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getTeacherIdByUserId(int userId) {
        try {
            String sql = "SELECT teacher_id FROM teachers WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
