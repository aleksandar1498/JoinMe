package com.enjoyit.domain.entities.compositekeys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRoleKey implements Serializable{

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;

    public UserRoleKey() {
        // NEEDED BY JPA
    }

    public UserRoleKey(final String userId, final String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserRoleKey other = (UserRoleKey) obj;
        return Objects.equals(this.userId, other.getUserId()) && Objects.equals(this.roleId,other.getRoleId());
    }

    public String getRoleId() {
        return roleId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, userId);
    }

    public void setRoleId(final String roleId) {
        this.roleId = roleId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
