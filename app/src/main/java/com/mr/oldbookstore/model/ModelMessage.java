package com.mr.oldbookstore.model;

public class ModelMessage {
    private String id;
    private String message;
    private String time;


    public ModelMessage() {
    }

    public ModelMessage(String id, String message, String time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}