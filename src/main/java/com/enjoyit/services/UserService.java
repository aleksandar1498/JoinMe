package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;

public interface UserService {
    /**
     * @param name
     * @param parseInt
     * @return
     */
    ServiceResponse<UserEventDTO> disinterestEvent(String username, String eventId);

    /**
     * @param name
     * @param parseInt
     * @return
     */
    ServiceResponse<UserEventDTO> disjoinEvent(String username, String eventId);

    /**
     * @return
     */
    List<UserWithRolesDTO> findAllUsers();

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
    ServiceResponse<UserEventDTO> interestEvent(String username, String eventId);


    /**
     * @param username
     * @param id
     * @return
     */
    ServiceResponse<UserEventDTO> joinEvent(String username, String id);

    /**
     * @param user
     * @return
     */
    ServiceResponse updateRoles(UserWithRolesDTO user);


}
