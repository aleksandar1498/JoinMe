package com.enjoyit.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.Role;

@Entity
@Table(name = "roles")
public class JpaRole implements Role {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

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
    @Override
    public String getId() {
        return id;
    }
    public void setAuthority(final UserRoles authority) {
        this.authority = authority;
    }
    public void setId(final String id) {
        this.id = id;
    }





}
