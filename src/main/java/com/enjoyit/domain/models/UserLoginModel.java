package com.enjoyit.domain.models;

public class UserLoginModel {
    private String username;
    private String password;

    public UserLoginModel() {
    }

    public UserLoginModel(final String username, final String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
