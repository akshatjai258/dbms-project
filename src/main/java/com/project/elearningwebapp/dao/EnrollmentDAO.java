package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Enrollment;
import com.project.elearningwebapp.models.Student;

import java.util.List;

public interface EnrollmentDAO {
    public void enroll(Enrollment enrollment);
    public void unEnroll(int studentId, int courseId);
    public Boolean isEnrolled(int studentId, int courseId);
    public int getTotalStudentEnrolled(int courseId);
    public List<Student>studentsEnrolled(int courseId);
    public List<Course>coursesEnrolled(int studentId);
    public Integer getNoOfStudentsEnrolled(int courseId);

}
