package com.enjoyit.persistence;

import java.time.LocalDateTime;
import java.util.List;

public interface Event {
    /**
     * @return
     */
    Boolean getBanned();

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
     * @param banned
     */
    void setBanned(Boolean banned);

    /**
     * @param state
     */
    void setCancelled(Boolean state);

    /**
     * @param location
     */
    void setLocation(Location location);
}
