package com.enjoyit.persistence.composite;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserJoinEventKey implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 6856370500749847289L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "event_id")
    private String eventId;

    public UserJoinEventKey() {
        // NEEDED BY JPA
    }

    public UserJoinEventKey(final String userId, final String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserJoinEventKey other = (UserJoinEventKey) obj;
        return Objects.equals(this.getUserId(), other.getUserId()) && this.getEventId().equals(other.getEventId());
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId);
    }

    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

}
