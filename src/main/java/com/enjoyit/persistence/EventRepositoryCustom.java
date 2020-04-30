package com.enjoyit.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.enjoyit.domain.entities.JpaEvent;

public interface EventRepositoryCustom {
    /**
     * @param title
     * @param location
     * @param startDate
     * @param endDate
     * @param owner
     * @return
     */
    Event createEvent(String title,String location,LocalDateTime startDate,LocalDateTime endDate,User owner);

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
    EventUser joinEvent(User user, Event event);
}
