package com.project.elearningwebapp.models;

import java.sql.Timestamp;

public class QuizAttempt {
    private Integer attemptId;
    private Student student;
    private Quiz quiz;

    @Override
    public String toString() {
        return "QuizAttempt{" +
                "attemptId=" + attemptId +
                ", student=" + student +
                ", quiz=" + quiz +
                ", marksGot=" + marksGot +
                ", totalMarks=" + totalMarks +
                '}';
    }

    public Integer getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getMarksGot() {
        return marksGot;
    }

    public void setMarksGot(Integer marksGot) {
        this.marksGot = marksGot;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public QuizAttempt() {
    }

    public QuizAttempt(Integer attemptId, Student student, Quiz quiz, Integer marksGot, Integer totalMarks) {
        this.attemptId = attemptId;
        this.student = student;
        this.quiz = quiz;
        this.marksGot = marksGot;
        this.totalMarks = totalMarks;
    }

    private Integer marksGot;
    private Integer totalMarks;


}
