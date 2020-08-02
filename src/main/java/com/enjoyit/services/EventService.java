package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;

/**
 * @author AStefanov
 *
 */
public interface EventService {
    /**
     * @param eventId
     * @return
     */
    void ban(String eventId);

    /**
     * @param id
     * @return
     */
    void cancelEventById(String id);
    /**
     * @return
     */
    int cleanUpExpiredEvents();

    /**
     * @param eventModel
     * @param username
     * @return
     */
    EventDTO createEvent(BaseEventDTO eventModel, String username);

    /**
     * @param id
     * @param event
     * @return
     */
    EventDTO editEventById(String id, BaseEventDTO event);
    /**
     * @return
     */
    List<EventDTO> getAllEvents();
    /**
     * @param name
     * @return
     */
    List<EventDTO> getAllOuterEvents(String name);
    /**
     * @param id
     * @return
     */
    EventDTO getEventById(String id);

    /**
     * @param owner
     * @return
     */
    List<EventDTO> getEventByOwner(String owner);

    List<EventDTO> getEventByLocation(String locationId);

}
