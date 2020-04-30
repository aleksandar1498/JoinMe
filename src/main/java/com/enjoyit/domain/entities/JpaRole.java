package com.enjoyit.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class JpaRole implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Column
    private String authority;

    public JpaRole() {
        // needed by JPA
    }
    public JpaRole(final String authority) {
        this.authority = authority;
    }



    @Override
    public String getAuthority() {

        return this.authority;
    }
    public String getId() {
        return id;
    }
    public void setAuthority(final String authority) {
        this.authority = authority;
    }
    public void setId(final String id) {
        this.id = id;
    }





}
