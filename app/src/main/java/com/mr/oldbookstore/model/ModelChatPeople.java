package com.mr.oldbookstore.model;

public class ModelChatPeople {
    private String name;
    private String imageUri;
    private String id;

    public ModelChatPeople() {
    }

    public ModelChatPeople(String name, String imageUri, String id) {
        this.name = name;
        this.imageUri = imageUri;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
