package com.enjoyit.domain.models;

import java.time.LocalDateTime;

public class EventCreateModel {
    private final String title;
    private final String locationId;
    private final String description;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     *
     * @param username of the creator of the event
     * @param title
     * @param location
     * @param description
     * @param startDate
     * @param endDate
     */
    public EventCreateModel(final String title, final String locationId, final String description, final LocalDateTime startDate,
            final LocalDateTime endDate) {
        this.title = title;
        this.locationId = locationId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getLocationId() {
        return locationId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "EventCreateModel [title=" + title + ", location=" + locationId + ", description=" + description
                + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
