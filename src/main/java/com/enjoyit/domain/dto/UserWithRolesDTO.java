package com.enjoyit.domain.dto;

import java.util.List;

public class UserWithRolesDTO {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Boolean banned;
    private List<RoleDTO> authorities;

    
    public UserWithRolesDTO(String id, String username, String name, String surname, String email, Boolean banned) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.banned = banned;
    }

    public UserWithRolesDTO() {
        // TODO Auto-generated constructor stub
    }

    public List<RoleDTO> getAuthorities() {
        return authorities;
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

    public void setAuthorities(final List<RoleDTO> authorities) {
        this.authorities = authorities;
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

    @Override
    public String toString() {
        return "UserWithRolesDTO [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname
                + ", email=" + email + ", authorities=" + authorities + "]";
    }





}
