package com.enjoyit.persistence.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.composite.UserInterestEventKey;

@SuppressWarnings("serial")
@Entity
public class JpaUserInterestEvent extends JpaEventUser {

    @EmbeddedId
    private UserInterestEventKey id;




    @ManyToOne(targetEntity = JpaUser.class)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = JpaEvent.class)
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    private Event event;

    public JpaUserInterestEvent() {
    }

    public JpaUserInterestEvent(final User user, final Event event) {
        this.user = user;
        this.event = event;
        this.id = new UserInterestEventKey(user.getId(), event.getId());
    }

    @Override
    public Event getEvent() {
        return this.event;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
