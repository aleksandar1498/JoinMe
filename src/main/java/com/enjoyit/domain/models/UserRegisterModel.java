package com.enjoyit.domain.models;

public class UserRegisterModel {
    private final String username;
    private final String password;
    private final String confirmPassword;
    private final String email;
    private final Boolean organizer;

    public UserRegisterModel(final String username, final String password, final String confirmPassword,
            final String email, final Boolean organizer) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.organizer = organizer;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isOrganizer() {
        return this.organizer;
    }


}
