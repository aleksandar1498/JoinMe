package com.enjoyit.services;

import java.util.List;
import java.util.Optional;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.models.EventCreateModel;

/**
 * @author AStefanov
 *
 */
public interface EventService {
    /**
     * @param id
     * @return
     */
    ServiceResponse cancelEventById(Integer id);
    /**
     * @param username
     * @return
     */
    ServiceResponse createEvent(EventCreateModel event,String username);

    /**
     * @param id
     * @param event
     * @return
     */
    ServiceResponse editEventById(Integer id, EventCreateModel event);

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
    Optional<EventDTO> getEventById(Integer id);
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
