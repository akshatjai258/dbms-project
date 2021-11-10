package com.project.elearningwebapp.models;

public class Topic {

    private int topicId;
    private String topicTitle;
    private String topicLecture;
    private String topicNotes;
    private int topicNumber;
    private Course course;

    public Topic(int topicId, String topicTitle, String topicLecture, String topicNotes, int topicNumber, Course course, int week) {
        this.topicId = topicId;
        this.topicTitle = topicTitle;
        this.topicLecture = topicLecture;
        this.topicNotes = topicNotes;
        this.topicNumber = topicNumber;
        this.course = course;
        this.week = week;
    }

    private int week;

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicLecture='" + topicLecture + '\'' +
                ", topicNotes='" + topicNotes + '\'' +
                ", topicNumber=" + topicNumber +
                ", course=" + course +
                ", week=" + week +
                '}';
    }

    public Topic() {
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicLecture() {
        return topicLecture;
    }

    public void setTopicLecture(String topicLecture) {
        this.topicLecture = topicLecture;
    }

    public String getTopicNotes() {
        return topicNotes;
    }

    public void setTopicNotes(String topicNotes) {
        this.topicNotes = topicNotes;
    }

    public int getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(int topicNumber) {
        this.topicNumber = topicNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
