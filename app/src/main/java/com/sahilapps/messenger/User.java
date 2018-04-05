package com.sahilapps.messenger;

public class User {
    private String user_name;
    private String image_url;
    private String user_id;

    public User() {
    }

    public User(String user_name, String image_url) {
        this.user_name = user_name;
        this.image_url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
