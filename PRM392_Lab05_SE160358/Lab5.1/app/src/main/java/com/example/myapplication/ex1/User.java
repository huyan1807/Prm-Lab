package com.example.myapplication.ex1;

public class User {
    private String username;
    private String fullname;
    private String email;

    // Constructor
    public User(String username, String fullname, String email) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
