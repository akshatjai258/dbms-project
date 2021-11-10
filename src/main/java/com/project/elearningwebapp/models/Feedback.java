package com.project.elearningwebapp.models;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Feedback {
    private int feedbackId;
    private String content;
    private Student student;
    private Course course;
    private Timestamp feedbackTimestamp;

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", content='" + content + '\'' +
                ", student=" + student +
                ", course=" + course +
                ", feedbackTimestamp=" + feedbackTimestamp +
                ", star=" + star +
                '}';
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Timestamp getTimestamp() {
        return feedbackTimestamp;
    }

    public void setTimestamp(Timestamp feedbackTimestamp) {
        this.feedbackTimestamp = feedbackTimestamp;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    private int star;

    public Feedback() {
    }

    public Feedback(int feedbackId, String content, Student student, Course course, Timestamp feedbackTimestamp, int star) {
        this.feedbackId = feedbackId;
        this.content = content;
        this.student = student;
        this.course = course;
        this.feedbackTimestamp = feedbackTimestamp;
        this.star = star;
    }
}
