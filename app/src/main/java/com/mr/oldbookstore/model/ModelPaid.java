package com.mr.oldbookstore.model;

public class ModelPaid {
    private String imageUri;
    private String book_title;
    private String price;

    public ModelPaid() {
    }

    public ModelPaid(String imageUri, String book_title, String price) {
        this.imageUri = imageUri;
        this.book_title = book_title;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
