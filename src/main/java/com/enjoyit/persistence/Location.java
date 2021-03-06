package com.enjoyit.persistence;

import java.util.List;

import com.enjoyit.enums.LocationCategory;

public interface Location {
    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    String getCity();

    /**
     *
     * @return
     */
    List<Event> getEvents();

    /**
     * @return
     */
    String getId();

    /**
     * @return
     */
    LocationCategory getLocationCategory();
}
