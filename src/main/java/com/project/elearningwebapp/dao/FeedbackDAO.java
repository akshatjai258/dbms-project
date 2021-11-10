package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Feedback;

import java.util.List;

public interface FeedbackDAO {
    public Feedback save(Feedback feedback);
    public Feedback get(int FeedbackId);
    public void delete(int FeedbackId);
    public void update(Feedback feedback);
    public List<Feedback> getByCourseId(int courseId);
    public List<Feedback> getByStudentId(int studentId);
    public Boolean isFeedbackGiven(int studentId, int courseId);
    public Feedback getByStudentAndCourseId(int studentId, int courseId);
    public Integer numberOfStars(int stars, int courseId);
    public List<Feedback> getTopFeedbacks(int courseId);

}
