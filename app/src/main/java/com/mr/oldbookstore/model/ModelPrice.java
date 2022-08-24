package com.mr.oldbookstore.model;

import android.net.Uri;

import java.security.PrivateKey;

public class ModelPrice {
    private String price;
    private String book_title;
    private String description;
    private String location;
    private String userID;
    private String category;
    private String imageUriMain;
    private String imageUriFront;
    private String imageUriBack;

    public ModelPrice(){

    }

    public ModelPrice(String price, String book_title, String description, String location, String userID, String category, String imageUriMain, String imageUriFront, String imageUriBack) {
        this.price = price;
        this.book_title = book_title;
        this.description = description;
        this.location = location;
        this.userID = userID;
        this.category = category;
        this.imageUriMain = imageUriMain;
        this.imageUriFront = imageUriFront;
        this.imageUriBack = imageUriBack;
    }

    public String getImageUriMain() {
        return imageUriMain;
    }

    public void setImageUriMain(String imageUriMain) {
        this.imageUriMain = imageUriMain;
    }

    public String getImageUriFront() {
        return imageUriFront;
    }

    public void setImageUriFront(String imageUriFront) {
        this.imageUriFront = imageUriFront;
    }

    public String getImageUriBack() {
        return imageUriBack;
    }

    public void setImageUriBack(String imageUriBack) {
        this.imageUriBack = imageUriBack;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
