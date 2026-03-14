package com.example.githubaccessreport.model;

public class Collaborator {

    private String login;
    private Permissions permissions;

    public String getLogin() {
        return login;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public static class Permissions {
        public boolean admin;
        public boolean push;
        public boolean pull;
    }
}