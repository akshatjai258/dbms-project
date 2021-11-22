package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.QuizAttempt;

import java.util.List;

public interface QuizAttemptDAO {

    public void save(QuizAttempt quizAttempt);
    public List<QuizAttempt> getByStudentId(int studentId);
    public List<QuizAttempt> getByQuizId(int quizId);
    public QuizAttempt getByStudentAndQuizID(int studentId, int quizId);

}
