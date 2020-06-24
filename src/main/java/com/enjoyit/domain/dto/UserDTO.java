package com.enjoyit.domain.dto;

public class UserDTO {
    private String id;
    private String username;

    public UserDTO() {
    }

    public UserDTO(final String id,final String username) {
        this.username = username;
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + "]";
    }


}
