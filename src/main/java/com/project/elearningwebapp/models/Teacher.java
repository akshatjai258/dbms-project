package com.project.elearningwebapp.models;

public class Teacher {
    private int teacherId;
    private String gender;
    private String houseNo;
    private String city;
    private String street;
    private String country;
    private String state;
    private String profilePic;
    private String aboutMe;
    private String pincode;
    private int noOfPhotosUploaded;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teacher(int teacherId, String gender, String houseNo, String city, String street, String country, String state, String profilePic, String aboutMe, String pincode, int NoOfPhotosUploaded, User user) {
        this.teacherId = teacherId;
        this.gender = gender;
        this.houseNo = houseNo;
        this.city = city;
        this.street = street;
        this.country = country;
        this.state = state;
        this.profilePic = profilePic;
        this.aboutMe = aboutMe;
        this.pincode = pincode;
        this.noOfPhotosUploaded = NoOfPhotosUploaded;
        this.user = user;
    }

    private User user;


    public Teacher() {
    }



    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", gender='" + gender + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", pincode='" + pincode + '\'' +
                ", noOfPhotosUploaded=" + noOfPhotosUploaded +
                ", user=" + user +
                '}';
    }

    public String getProfilePath(){
        return "/images/profile/teacher/" + this.getProfilePic();
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getNoOfPhotosUploaded() {
        return noOfPhotosUploaded;
    }

    public void setNoOfPhotosUploaded(int NoOfPhotosUploaded) {
        this.noOfPhotosUploaded = NoOfPhotosUploaded;
    }
}
