package com.enjoyit.domain.dto;

public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Boolean banned;

    public UserDTO() {
    }

    public UserDTO(final String id, final String username, final String name, final String surname,
            final String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Boolean getBanned() {
        return banned;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public void setBanned(final Boolean banned) {
        this.banned = banned;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
