package com.project.elearningwebapp.dao;


import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseDAO {
    public Course save(Course course);
    public void update(Course course);
    public void delete(int courseId);
    public Course get(int courseId);
    public Page<Course> findAll(Pageable page);
    public List<Course>sortByRating();
    public List<Course>sortByEnrolments();
    public List<Course>sortByDate();
    public List<Course>getAllByTeacherId(int teacherId);
    public List<Course>getAllByCategoryId(int categoryId);
    public List<Course>searchedCourses(String text);
    public List<Course>getAllByDifficulty(String difficulty);
    public Page<Course>advanceFilter(String course_text, String category_text, String difficulty_text, Pageable page);
    public Integer advanceCount(String course_text, String category_text, String difficulty_text);
    public int count();
}
