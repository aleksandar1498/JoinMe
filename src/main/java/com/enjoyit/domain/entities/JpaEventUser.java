package com.enjoyit.domain.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

@Entity
@NamedQuery(name = JpaEventUser.EVENTS_BY_PATIENT_USERNAME,
query = "SELECT e FROM JpaEvent e INNER JOIN JpaEventUser s ON e.id = s.event.id INNER JOIN JpaUser u ON s.user.id = u.id WHERE u.username = :username")
public class JpaEventUser implements EventUser{

    public static final String EVENTS_BY_PATIENT_USERNAME = "eventsByPatientUsername";
    @EmbeddedId
    EventUserKey id;

    @ManyToOne(targetEntity = JpaUser.class)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(targetEntity = JpaEvent.class)
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    Event event;

    public JpaEventUser() {
        // TODO Auto-generated constructor stub
    }

    public JpaEventUser(final User user, final Event event) {
        this.event = event;
        this.user = user;
        this.id = new EventUserKey(user.getId(),event.getId());
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public void setUser(final User user) {
        this.user = user;
    }


}
