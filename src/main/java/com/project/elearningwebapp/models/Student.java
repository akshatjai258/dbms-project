package com.project.elearningwebapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class Student {
    private int studentId;
    private String gender;
    private String dateOfBirth;
    private String houseNo;
    private String city;
    private String street;
    private String country;
    private String state;
    private String profilePic;

    public int getNoOfPhotosUploaded() {
        return noOfPhotosUploaded;
    }

    public void setNoOfPhotosUploaded(int noOfPhotosUploaded) {
        this.noOfPhotosUploaded = noOfPhotosUploaded;
    }

    private int noOfPhotosUploaded;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    private String pincode;
    private User user;

    public Student() {

    }

    public Student(int studentId, int noOfPhotosUploaded, String gender, String dateOfBirth, String houseNo, String city, String street, String country, String state, String profilePic, String pincode, User user) {
        this.studentId = studentId;
        this.gender = gender;
        this.noOfPhotosUploaded = noOfPhotosUploaded;
        this.dateOfBirth = dateOfBirth;
        this.houseNo = houseNo;
        this.city = city;
        this.street = street;
        this.country = country;
        this.state = state;
        this.profilePic = profilePic;
        this.pincode = pincode;
        this.user = user;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProfilePath(){
        return "/images/profile/student/" + this.getProfilePic();
    }


}
