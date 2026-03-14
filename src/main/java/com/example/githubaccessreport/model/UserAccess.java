package com.example.githubaccessreport.model;

import java.util.List;

public class UserAccess {

    private String username;
    private List<String> repositories;

    public UserAccess(String username, List<String> repositories) {
        this.username = username;
        this.repositories = repositories;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRepositories() {
        return repositories;
    }
}