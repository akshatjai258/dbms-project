package com.project.elearningwebapp.models;

import java.sql.Timestamp;


public class Course {
    private int courseId;
    private Teacher teacher;
    private String courseName;
    private String courseDescription;
    private double coursePrice;
    private double avgRating;
    private Timestamp timestamp;
    private Category category;
    private String courseThumbnail;

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", teacher=" + teacher +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", coursePrice=" + coursePrice +
                ", avgRating=" + avgRating +
                ", timestamp=" + timestamp +
                ", category=" + category +
                ", courseThumbnail='" + courseThumbnail + '\'' +
                ", noOfWeeks=" + noOfWeeks +
                ", courseDifficulty='" + courseDifficulty + '\'' +
                ", courseOverview='" + courseOverview + '\'' +
                '}';
    }

    private int noOfWeeks;

    private String courseDifficulty;

    public String getCourseOverview() {
        return courseOverview;
    }

    public void setCourseOverview(String courseOverview) {
        this.courseOverview = courseOverview;
    }

    private String courseOverview;

    public Course() {
    }

    public Course(int course_id, Teacher teacher, String courseName, String courseDescription, int coursePrice, double avgRating, Timestamp timestamp, Category category, String courseThumbnail, int noOfWeeks, String courseDifficulty, String courseOverview) {
        this.courseId = course_id;
        this.teacher = teacher;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.coursePrice = coursePrice;
        this.avgRating = avgRating;
        this.timestamp = timestamp;
        this.category = category;
        this.courseThumbnail = courseThumbnail;
        this.noOfWeeks = noOfWeeks;
        this.courseDifficulty = courseDifficulty;
        this.courseOverview = courseOverview;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCourseThumbnail() {
        return courseThumbnail;
    }

    public void setCourseThumbnail(String courseThumbnail) {
        this.courseThumbnail = courseThumbnail;
    }

    public String getCourseDifficulty() {
        return courseDifficulty;
    }

    public void setCourseDifficulty(String courseDifficulty) {
        this.courseDifficulty = courseDifficulty;
    }

    public int getNoOfWeeks() {
        return noOfWeeks;
    }

    public void setNoOfWeeks(int noOfWeeks) {
        this.noOfWeeks = noOfWeeks;
    }

    public String getThumbnailPath(){
        return "/images/course-thumbnails/" + this.getCourseId() +"/"+ this.getCourseThumbnail();
    }
}
