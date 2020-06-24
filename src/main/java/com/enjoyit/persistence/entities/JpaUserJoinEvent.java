package com.enjoyit.persistence.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.composite.UserJoinEventKey;

@Entity
@NamedQuery(name = JpaUserJoinEvent.EVENTS_BY_USER_USERNAME,
        query = "SELECT e FROM JpaEvent e INNER JOIN JpaUserJoinEvent s ON e.id = s.event.id INNER JOIN JpaUser u ON s.user.id = u.id WHERE u.username = :username")
public class JpaUserJoinEvent extends JpaEventUser {

    public static final String EVENTS_BY_USER_USERNAME = "eventsByUsername";

    @EmbeddedId
    private UserJoinEventKey id;

    @ManyToOne(targetEntity = JpaUser.class)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne(targetEntity = JpaEvent.class)
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    private Event event;

    public JpaUserJoinEvent() {
    }

    public JpaUserJoinEvent(final User user, final Event event) {
        this.user = user;
        this.event = event;
        this.id = new UserJoinEventKey(user.getId(), event.getId());
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
