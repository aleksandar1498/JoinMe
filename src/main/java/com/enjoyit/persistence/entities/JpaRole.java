package com.enjoyit.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.Role;

@Entity
@Table(name = "roles")
public class JpaRole extends BaseEntity implements Role {

    @Enumerated(EnumType.STRING)
    private UserRoles authority;

    public JpaRole() {
        // needed by JPA
    }

    public JpaRole(final UserRoles authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority.toString();
    }

    public void setAuthority(final UserRoles authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "JpaRole [authority=" + authority + "]";
    }



}
