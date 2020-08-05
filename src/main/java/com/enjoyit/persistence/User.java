package com.enjoyit.persistence;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails {

    /**
     * @return
     */
    Boolean getBanned();

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

    /**
     * @return
     */
    String getName();
    /**
     * @return
     */
    String getSurname();

    @Override
    String getUsername();

    /**
     * @param roles
     */
    void setAuthorities(Set<Role> roles);

    List<Notification> getNotifications();
}
