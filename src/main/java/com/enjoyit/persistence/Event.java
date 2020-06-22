package com.enjoyit.persistence;

import java.time.LocalDateTime;
import java.util.List;

public interface Event {
    /**
     * @return
     */
    Boolean getCancelled();
    /**
     * @return
     */
    String getDescription();

    /**
     * @return
     */
    LocalDateTime getEndDate();
    /**
     * @return
     */
    Integer getId();

    /**
     * @return
     */
    List<EventUser> getInterestedUsers();
    /**
     * @return
     */
    List<EventUser> getJoinedUsers();
    /**
     * @return
     */
    String getLocation();

    /**
     * @return
     */
    User getOwner();

    /**
     * @return
     */
    LocalDateTime getStartDate();

    /**
     * @return
     */
    String getTitle();
    /**
     * @return
     */
    void setCancelled();
}
