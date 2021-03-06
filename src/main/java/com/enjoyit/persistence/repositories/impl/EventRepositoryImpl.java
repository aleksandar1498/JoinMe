package com.enjoyit.persistence.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.composite.UserInterestEventKey;
import com.enjoyit.persistence.composite.UserJoinEventKey;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaUserInterestEvent;
import com.enjoyit.persistence.entities.JpaUserJoinEvent;
import com.enjoyit.persistence.entities.stats.UserEventStatistic;
import com.enjoyit.persistence.repositories.EventRepositoryCustom;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public int cancelExpired() {
        return this.entityManager.createNamedQuery(JpaEvent.CLEAR_EXPIRED).executeUpdate();
    }

    @Override
    public void disinterestEvent(final User user, final Event event) {
        final EventUser interestToRemove = this.entityManager.find(JpaUserInterestEvent.class, new UserInterestEventKey(user.getId(), event.getId()));
        this.entityManager.remove(interestToRemove);
        this.entityManager.flush();
    }

    @Override
    public void disjoinEvent(final User user, final Event event) {
        final EventUser joinToRemove = this.entityManager.find(JpaUserJoinEvent.class, new UserJoinEventKey(user.getId(), event.getId()));
        this.entityManager.remove(joinToRemove);
        this.entityManager.flush();
    }

    @Override
    public List<JpaEvent> getEventsNotBelongingTo(final String username) {
        return this.entityManager.createNamedQuery(JpaEvent.EVENTS_NOT_BELONGING_TO_USERNAME, JpaEvent.class)
                .setParameter("username", username).getResultList();
    }


    @Override
    public EventUser interestEvent(final User user, final Event event) {
        final EventUser interest = new JpaUserInterestEvent(user, event);
        System.out.println(interest);
        this.entityManager.persist(interest);
        this.entityManager.flush();

        return interest;
    }

    @Override
    public EventUser joinEvent(final User user, final Event event) {
        final JpaUserJoinEvent join = new JpaUserJoinEvent(user, event);
        this.entityManager.persist(join);
        this.entityManager.flush();
        return join;
    }

    @Override
    public List<UserEventStatistic> getEventsStatistic(final String id) {
        return this.entityManager.createNamedQuery(JpaEvent.EVENTS_FOR_USER_STATISTIC).setParameter("id",id).getResultList();
    }
}
