package com.enjoyit.domain.dto;

import com.enjoyit.common.validators.PasswordMatchingValidator;

@PasswordMatchingValidator
public class UserRegisterDTO {
    private final String username;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final String confirmPassword;
    private final Boolean organizer;

    public UserRegisterDTO(final String username, final String name, final String surname, final String email, final String password,
            final String confirmPassword, final Boolean organizer) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.organizer = organizer;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Boolean getOrganizer() {
        return organizer;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isOrganizer() {
        return this.organizer;
    }



}
