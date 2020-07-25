package com.enjoyit.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.enjoyit.common.validators.PasswordMatchingValidator;

@PasswordMatchingValidator
public class UserRegisterDTO {
    @NotEmpty(message = "*username cannot be empty")
    private final String username;

    @NotEmpty(message = "*name cannot be empty")
    private final String name;

    @NotEmpty(message = "*surname cannot be empty")
    private final String surname;

    @NotEmpty(message = "*email cannot be empty")
    @Email(message = "*email should be in valid format")
    private final String email;

    @NotEmpty(message = "*password cannot be empty")
    private final String password;

    @NotEmpty(message = "*confirmPassword cannot be empty")
    private final String confirmPassword;

    private final Boolean organizer;

    public UserRegisterDTO(final String username, final String name, final String surname, final String email,
            final String password, final String confirmPassword, final Boolean organizer) {
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
