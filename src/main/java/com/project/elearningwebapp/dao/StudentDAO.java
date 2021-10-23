package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Student;

import java.util.List;

public interface StudentDAO {
    public Student save(Student student);

    public List<Student> getAll();

    public List<Student> getAllByCourseId(String courseId);

    public Student get(int studentId);

    public Student getByUserId(int userId);

    public Integer getStudentIdByUserId(int userId);

    public void update(Student student);

    public void delete(int studentId);
}
