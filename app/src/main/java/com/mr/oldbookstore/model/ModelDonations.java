package com.mr.oldbookstore.model;

public class ModelDonations {
    private String imageUri;
    private String book_title;

    public ModelDonations() {
    }

    public ModelDonations(String imageUri, String book_title) {
        this.imageUri = imageUri;
        this.book_title = book_title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }
}
