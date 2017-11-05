package com.example.nguye.minisafeway.Model;

/**
 * Created by nguye on 11/5/2017.
 */

public class User {

    private String Name;
    private String Password;
    private String email;

    public User() {
    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public User(String name, String password, String email) {
        Name = name;
        Password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;

    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
