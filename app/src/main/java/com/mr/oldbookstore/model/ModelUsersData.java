package com.mr.oldbookstore.model;

import android.view.Display;

import com.google.firebase.database.DatabaseReference;

public class ModelUsersData {
    private String phoneNumber;
    private String name;
    private String email;
    private String city;
    private String point;
    private String userID;
    private String imageUri;




    public ModelUsersData(){

    }

    public ModelUsersData(String phoneNumber, String name, String email, String city, String point, String userID, String imageUri) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.city = city;
        this.point = point;
        this.userID = userID;
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
