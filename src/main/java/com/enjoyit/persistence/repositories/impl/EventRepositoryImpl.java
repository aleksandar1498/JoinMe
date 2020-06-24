package com.enjoyit.persistence.repositories.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyit.enums.EventCategory;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.composite.UserInterestEventKey;
import com.enjoyit.persistence.composite.UserJoinEventKey;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaUserInterestEvent;
import com.enjoyit.persistence.entities.JpaUserJoinEvent;
import com.enjoyit.persistence.repositories.EventRepositoryCustom;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Event createEvent(final String title, final String location, final LocalDateTime startDate,
            final LocalDateTime endDate, final User owner,final EventCategory category,final String description) {

        final Event event = new JpaEvent(title, location, startDate, endDate, owner,category,description);
        this.entityManager.persist(event);
        return event;
    }

    @Override
    public void disinterestEvent(final User user, final Event event) {
        final EventUser interestToRemove = this.entityManager.find(JpaUserInterestEvent.class, new UserInterestEventKey(user.getId(), event.getId()));
        this.entityManager.remove(interestToRemove);
    }

    @Override
    public void disjoinEvent(final User user, final Event event) {
        final EventUser joinToRemove = this.entityManager.find(JpaUserJoinEvent.class, new UserJoinEventKey(user.getId(), event.getId()));
        this.entityManager.remove(joinToRemove);
    }

    @Override
    public List<JpaEvent> getEventsNotBelongingTo(final String username) {
        return this.entityManager.createNamedQuery(JpaEvent.EVENTS_NOT_BELONGING_TO_USERNAME, JpaEvent.class)
                .setParameter("username", username).getResultList();
    }

    @Override
    public List<JpaEvent> getJoinedEvents(final String username) {
        return this.entityManager.createNamedQuery(JpaUserJoinEvent.EVENTS_BY_USER_USERNAME, JpaEvent.class)
                .setParameter("username", username).getResultList();
    }

    @Override
    public EventUser interestEvent(final User user, final Event event) {
        final EventUser interest = new JpaUserInterestEvent(user, event);
        this.entityManager.persist(interest);
        return interest;
    }

    @Override
    public EventUser joinEvent(final User user, final Event event) {
        final JpaUserJoinEvent join = new JpaUserJoinEvent(user, event);
        System.out.println(join.getUser().getUsername()+" "+join.getEvent().getTitle());
        this.entityManager.persist(join);
        return join;
    }
}
