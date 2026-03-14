package com.example.githubaccessreport.model;

import java.util.List;

public class AccessReport {

    private String repository;
    private List<UserAccess> users;

    public AccessReport() {
    }

    public AccessReport(String repository, List<UserAccess> users) {
        this.repository = repository;
        this.users = users;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public List<UserAccess> getUsers() {
        return users;
    }

    public void setUsers(List<UserAccess> users) {
        this.users = users;
    }
}