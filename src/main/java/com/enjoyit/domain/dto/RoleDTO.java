package com.enjoyit.domain.dto;

import com.enjoyit.enums.UserRoles;

public class RoleDTO {
    private UserRoles authority;

    public RoleDTO() {
        // TODO Auto-generated constructor stub
    }

    public RoleDTO(final String role) {
        this.authority = UserRoles.valueOf(role);
    }

    public RoleDTO(final UserRoles role) {
        this.authority = role;
    }

    public UserRoles getAuthority() {
        return authority;
    }

    public void setAuthority(final UserRoles authority) {
        this.authority = authority;
    }

}
