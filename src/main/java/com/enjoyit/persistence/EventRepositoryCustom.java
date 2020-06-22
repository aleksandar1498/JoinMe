package com.enjoyit.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.enjoyit.domain.entities.JpaEvent;
import com.enjoyit.enums.EventCategory;

public interface EventRepositoryCustom {
    /**
     * @param title
     * @param location
     * @param startDate
     * @param endDate
     * @param owner
     * @param category
     * @return
     */
    Event createEvent(String title, String location, LocalDateTime startDate, LocalDateTime endDate, User owner,
            EventCategory category,String description);

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
     * @param username
     * @return
     */
    List<JpaEvent> getJoinedEvents(String username);


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
