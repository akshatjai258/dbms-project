package com.project.elearningwebapp.models;

public class Quiz {
    private Integer quizId;
    private String quizTitle;
    private String quizDescription;
    private String quizInstructions;
    private Integer week;
    private Course course;
    private Integer quizTime;

    public Quiz() {
    }

    public Quiz(Integer quizId, String quizTitle, String quizDescription, String quizInstructions, Integer week, Course course, Integer quizTime) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizDescription = quizDescription;
        this.quizInstructions = quizInstructions;
        this.week = week;
        this.course = course;
        this.quizTime = quizTime;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public String getQuizInstructions() {
        return quizInstructions;
    }

    public void setQuizInstructions(String quizInstructions) {
        this.quizInstructions = quizInstructions;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(Integer quizTime) {
        this.quizTime = quizTime;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", quizTitle='" + quizTitle + '\'' +
                ", quizDescription='" + quizDescription + '\'' +
                ", quizInstructions='" + quizInstructions + '\'' +
                ", week=" + week +
                ", course=" + course +
                ", quizTime=" + quizTime +
                '}';
    }
}
