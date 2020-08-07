package com.enjoyit.domain.dto;

import com.enjoyit.enums.UserRoles;

public class RoleDTO {
    private UserRoles role;

    public RoleDTO() {
        // TODO Auto-generated constructor stub
    }

    public RoleDTO(final String role) {
        this.role = UserRoles.valueOf(role);
    }

    public RoleDTO(final UserRoles role) {
        this.role = role;
    }

    public UserRoles getAuthority() {
        return role;
    }

    public void setAuthority(final UserRoles role) {
        this.role = role;
    }

}
