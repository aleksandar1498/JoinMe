package com.enjoyit.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.enjoyit.persistence.Notification;
import com.enjoyit.persistence.User;

@Entity
@Table(name = "notifications")
@NamedQuery(name = JpaNotification.GET_ALL_BY_RECIPIENT,
query = "SELECT n FROM JpaNotification n  WHERE n.recipient.username=:username")
public class JpaNotification extends BaseEntity implements Notification {

    public static final String GET_ALL_BY_RECIPIENT = "findAllByRecipient";
    @Column(columnDefinition = "TEXT")
    private String message;
    @ManyToOne(targetEntity = JpaUser.class)
    private User recipient;
    @Column
    private Boolean isViewed;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public User getRecipient() {
        return recipient;
    }

    @Override
    public Boolean getIsViewed() {
        return isViewed;
    }

    @Override
    public void setIsViewed(final Boolean isViewed) {
        this.isViewed = isViewed;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setRecipient(final User recipient) {
        this.recipient = recipient;
    }





}
