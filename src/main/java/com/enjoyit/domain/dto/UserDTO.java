package com.enjoyit.domain.dto;

public class UserDTO {
    private String username;

    public UserDTO() {
    }

    public UserDTO(final String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(final String username) {
        this.username = username;
    }

}
