package com.example.logins.users;

public class User {

    String name;
    String email;
    int image;

    public User(String name, String message, int image) {
        this.name = name;
        this.email = message;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
