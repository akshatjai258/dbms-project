package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.User;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class userDAOImpl implements userDAO {
    public userDAOImpl() {
    }

    public userDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;



    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, enabled, first_name, last_name, email_id, role) "
                + "VALUES (?, ?, ?, ?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] { "user_id" });
                preparedStatementUtil.setParameters(preparedStatement, user.getUsername(), user.getPassword(),user.getEnabled(), user.getFirstName(),
                         user.getLastName(), user.getEmailId(), user.getRole());
                return preparedStatement;
            }

        }, keyHolder);

        int userId = keyHolder.getKey().intValue();
        user.setUser_id(userId);
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public User get(int userId) {
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            return (User) jdbcTemplate.queryForObject(sql, new Object[] { userId },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getPassword(int userId) {
        try {
            String sql = "SELECT password FROM users WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            return (User) jdbcTemplate.queryForObject(sql, new Object[] { username },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email_id = ?";
            return (User) jdbcTemplate.queryForObject(sql, new Object[] { email },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void activate(int userId) {
        String sql = "UPDATE users SET enabled = true WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void changePassword(int userId, String password) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, password, userId);
    }

    @Override
    public String getFullName(int userId) {

        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            User user = (User) jdbcTemplate.queryForObject(sql, new Object[] { userId },
                    new BeanPropertyRowMapper<>(User.class));
            String fullName = user.getFirstName() + " " + user.getLastName();
            return fullName;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void setRole(User user, String role) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, role, user.getUser_id());
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET first_name=?, last_name=?,"
                + "email_id = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmailId(), user.getUser_id());
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
