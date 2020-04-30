package com.enjoyit.domain.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EventUserKey implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6856370500749847289L;

    @Column(name = "user_id")
    String userId;

    @Column(name = "event_id")
    int eventId;

    public EventUserKey() {
        // NEEDED BY JPA
    }

    public EventUserKey(final String userId, final Integer eventId) {
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
        final EventUserKey other = (EventUserKey) obj;
        return Objects.equals(this.userId, other.userId) && this.eventId == other.eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId);
    }

    public void setEventId(final int eventId) {
        this.eventId = eventId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

}
