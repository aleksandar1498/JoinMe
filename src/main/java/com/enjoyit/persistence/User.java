package com.enjoyit.persistence;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails {

    String getEmail();

    /**
     * @return
     */
    List<Event> getEvents();

    /**
     * @return
     */
    String getId();

    List<EventUser> getInterestedEvents();

    /**
     * @return
     */
    List<EventUser> getJoinedEvents();

    String getName();

    String getSurname();

    @Override
    String getUsername();
}
