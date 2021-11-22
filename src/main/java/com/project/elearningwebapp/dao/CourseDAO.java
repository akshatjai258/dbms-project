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
    public Page<Course>getAllByTeacherId(int teacherId, Pageable pageable);
    public List<Course>getAllByCategoryId(int categoryId);
    public List<Course>searchedCourses(String text);
    public List<Course>getAllByDifficulty(String difficulty);
    public Page<Course>advanceFilter(String course_text, Integer category_id, String difficulty_text, Pageable page);
    public Integer advanceCount(String course_text, Integer category_id, String difficulty_text);
    public int count();
    public int count_new(int teacherId);
    public int numOfLectures(int teacherId);
    public void updateRating(int courseId, double avgRating);

}
