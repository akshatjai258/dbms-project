package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface userDAO {
    public User save(User user);

    public List<User> findAll();

    public User get(int userId);

    public String getPassword(int userId);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public void activate(int userId);

    public void changePassword(int userId, String password);

    public String getFullName(int userId);

    public void setRole(User user, String role);

    public void update(User user);

    public void delete(int userId);
}
