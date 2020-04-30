package com.enjoyit.persistence;

import java.time.LocalDateTime;

public interface Event {
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
}
