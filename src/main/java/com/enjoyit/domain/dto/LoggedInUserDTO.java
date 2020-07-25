package com.enjoyit.domain.dto;

public class LoggedInUserDTO {
    private String username;
    private String token;

    public LoggedInUserDTO(final String username, final String token) {
        this.username = username;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
