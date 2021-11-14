package com.project.elearningwebapp.dao;


import com.project.elearningwebapp.models.Quiz;

import java.util.List;

public interface QuizDAO {
    public Quiz save(Quiz quiz);
    public void update(Quiz quiz);
    public void delete(Integer quizId);
    public Quiz get(int QuizId);
    public List<Quiz> findAllByCourseId(int courseId);



}
