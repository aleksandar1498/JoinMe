package com.enjoyit.persistence.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyit.domain.entities.JpaEvent;
import com.enjoyit.domain.entities.JpaEventUser;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventRepositoryCustom;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Event createEvent(final String title, final String location, final LocalDateTime startDate,
            final LocalDateTime endDate, final User owner) {

        final Event event = new JpaEvent(title, location, startDate, endDate, owner);
        this.entityManager.persist(event);
        return event;
    }

    @Override
    public List<JpaEvent> getJoinedEvents(final String username) {
        return this.entityManager.createNamedQuery(JpaEventUser.EVENTS_BY_PATIENT_USERNAME, JpaEvent.class)
                .setParameter("username", username).getResultList();
    }

    @Override
    public EventUser joinEvent(final User user, final Event event) {
        final EventUser join = new JpaEventUser(user, event);
        this.entityManager.persist(join);
        return join;
    }

}
