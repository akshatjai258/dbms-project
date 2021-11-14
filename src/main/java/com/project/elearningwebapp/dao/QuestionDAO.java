package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Question;

import java.util.List;

public interface QuestionDAO {
    public Question save(Question question);
    public void delete(Integer questionId);
    public void update(Question question);
    public Question get(int questionId);
    public List<Question>findByQuizId(int quizId);

}
