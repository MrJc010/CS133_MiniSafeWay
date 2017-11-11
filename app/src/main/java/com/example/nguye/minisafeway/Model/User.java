package com.example.nguye.minisafeway.Model;

import java.util.List;

/**
 * Created by nguye on 11/5/2017.
 */

public class User {
    private String id;
    private String Name;
    private String Password;
    private List<Request> requests;

    public User() {
    }

    public User(String id, String name, String password, List<Request> requests) {
        this.id = id;
        Name = name;
        Password = password;
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
