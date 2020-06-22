package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;

public interface UserService {
    /**
     * @param name
     * @param parseInt
     * @return
     */
    ServiceResponse<UserEventDTO> disinterestEvent(String username, int eventId);

    /**
     * @param name
     * @param parseInt
     * @return
     */
    ServiceResponse<UserEventDTO> disjoinEvent(String username, int eventId);

    /**
     * @param username
     * @return
     */
    UserWithEventsDTO findByUsername(String username);

    /**
     * @param username
     * @return
     */
    List<EventDTO> getInterestedEvents(String username);

    /**
     * @param username
     * @return
     */
    List<EventDTO> getJoinedEvents(String username);

    /**
     * @param name
     * @param parseInt
     * @return
     */
    ServiceResponse<UserEventDTO> interestEvent(String username, int eventId);

    /**
     * @param username
     * @param id
     * @return
     */
    ServiceResponse<UserEventDTO> joinEvent(String username, int eventId);


}
