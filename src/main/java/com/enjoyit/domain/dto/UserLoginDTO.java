package com.enjoyit.domain.dto;

import javax.validation.constraints.NotEmpty;

public class UserLoginDTO {
    @NotEmpty(message = "*username must not be empty")
    private String username;

    @NotEmpty(message = "*password must not be empty")
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(final String username, final String password) {
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
