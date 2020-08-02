package com.enjoyit.services;

import java.util.List;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.InterestEventDTO;
import com.enjoyit.domain.dto.JoinEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;

public interface UserService {
    /**
     * @param username
     */
    void authorize(String username);

    /**
     * @param userId
     * @return
     */
    void ban(String username);

    /**
     * @param name
     * @param parseInt
     * @return
     */
    void disinterestEvent(String username, String eventId);

    /**
     * @param name
     * @param parseInt
     * @return
     */
    void disjoinEvent(String username, String eventId);

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
    InterestEventDTO interestEvent(String username, String eventId);

    /**
     * @param username
     * @param id
     * @return
     */
    JoinEventDTO joinEvent(String username, String id);

    /**
     * @param user
     * @return
     */
    UserWithRolesDTO updateRoles(UserWithRolesDTO user);


}
