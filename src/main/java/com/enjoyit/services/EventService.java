package com.enjoyit.services;

import java.util.List;
import java.util.Optional;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;

/**
 * @author AStefanov
 *
 */
public interface EventService {
    /**
     * @param id
     * @return
     */
    ServiceResponse cancelEventById(String id);
//    /**
//     * @param username
//     * @return
//     */
//    ServiceResponse createEvent(EventDTO event,String username);

    ServiceResponse createEvent(BaseEventDTO eventModel, String username);

    /**
     * @param id
     * @param event
     * @return
     */
    ServiceResponse editEventById(String id, BaseEventDTO event);

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
    Optional<EventDTO> getEventById(String id);
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
