package com.sahilapps.messenger;

public class Notification {
    private String user_name;
    private String message;
    private String image_url;

    public Notification() {
    }

    public Notification(String user_name, String message, String image_url) {
        this.user_name = user_name;
        this.message = message;
        this.image_url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}