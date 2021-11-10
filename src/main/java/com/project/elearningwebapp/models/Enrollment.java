package com.project.elearningwebapp.models;

import java.sql.Timestamp;

public class Enrollment {
    private int enrollmentId;
    private Student student;

    public Enrollment(int enrollmentId, Student student, Course course, Timestamp enrollment_time) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollment_time = enrollment_time;
    }

    public Enrollment() {
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", student=" + student +
                ", course=" + course +
                ", enrollment_time=" + enrollment_time +
                '}';
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
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

    public Timestamp getEnrollment_time() {
        return enrollment_time;
    }

    public void setEnrollment_time(Timestamp enrollment_time) {
        this.enrollment_time = enrollment_time;
    }

    private Course course;
    private Timestamp enrollment_time;

}
