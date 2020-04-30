package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;

public interface UserService {
    /**
     * @param username
     * @return
     */
    UserWithEventsDTO findByUsername(String username);

    /**
     * @param username
     * @return
     */
    List<EventDTO> getJoinedEvents(String username);

    /**
     * @param username
     * @param id
     * @return
     */
    UserEventDTO joinEvent(String username, int id);
}
