package com.mixoor.khademni.payload.response;


import java.util.Date;

public class UserProfile {

    private Long id;
    private String name;
    private String aboutMe;
    private String country;
    private String city;
    private Date dob;
    private String adresse;
    private String role;
    private String picture;
    private Date replyTime;
    private float rating;

    public UserProfile(Long id, String name, String aboutMe, String country, String city, Date dob, String adresse, String role, String picture, float rating) {
        this.id = id;
        this.name = name;
        this.aboutMe = aboutMe;
        this.country = country;
        this.city = city;
        this.dob = dob;
        this.adresse = adresse;
        this.role = role;
        this.picture = picture;
        this.rating = rating;
    }


    public UserProfile(Long id, String name, String aboutMe, String country, String city, Date dob, String adresse, String role, String picture, Date replyTime, float rating) {
        this.id = id;
        this.name = name;
        this.aboutMe = aboutMe;
        this.country = country;
        this.city = city;
        this.dob = dob;
        this.adresse = adresse;
        this.role = role;
        this.picture = picture;
        this.replyTime = replyTime;
        this.rating = rating;

    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}
