package com.enjoyit.persistence.repositories;

import java.util.List;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.entities.JpaEvent;

public interface EventRepositoryCustom {



    /**
     *
     */
    int cancelExpired();
    /**
     * @param user
     * @param event
     */
    void disinterestEvent(User user, Event event);
    /**
     * @param user
     * @param event
     */
    void disjoinEvent(User user, Event event);

    /**
     * @param username
     * @return
     */
    List<JpaEvent> getEventsNotBelongingTo(String username);

    /**
     * @param user
     * @param event
     * @return
     */
    EventUser interestEvent(User user, Event event);

    /**
     * @param user
     * @param event
     * @return
     */
    EventUser joinEvent(User user, Event event);
}
