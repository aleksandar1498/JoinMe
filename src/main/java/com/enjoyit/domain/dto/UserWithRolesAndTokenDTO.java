package com.enjoyit.domain.dto;

import java.util.List;

import com.enjoyit.enums.UserRoles;

public class UserWithRolesAndTokenDTO extends UserDTO {
    private final String token;
    private final List<UserRoles> roles;

    public UserWithRolesAndTokenDTO(final String username, final String token, final List<UserRoles> roles) {
        super(username);
        this.token = token;
        this.roles = roles;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }

    public String getToken() {
        return token;
    }

}
