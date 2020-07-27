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
    String getId();

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
    Location getLocation();

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

    void setLocation(Location location);
}
