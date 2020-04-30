package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.EventDTO;

public interface EventService {
    /**
     * @param username
     * @return
     */
    EventDTO createEvent(String username);
    /**
     * @return
     */
    List<EventDTO> getAllEvents();

    /**
     * @param location
     * @return
     */
    List<EventDTO> getEventByLocation(String location);

    /**
     * @param owner
     * @return
     */
    List<EventDTO> getEventByOwner(String owner);

    /**
     * @param owner
     * @return
     */
    List<EventDTO> getJoinedEvents(String username);
}
