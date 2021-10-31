package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Teacher;

import java.util.List;

public interface TeacherDAO {
    public Teacher save(Teacher Teacher);

    public void update(Teacher Teacher);

    public void delete(int TeacherId);

    public List<Teacher> getAll();

    public Teacher get(int TeacherId);

    public Teacher getByUserId(int userId);

    public Integer getTeacherIdByUserId(int userId);
}
