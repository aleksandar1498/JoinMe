package com.enjoyit.domain.dto;

import java.time.LocalDateTime;

public class BaseEventDTO {
    private String title;
    private LocationDTO location;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public BaseEventDTO() {
    }

    /**
     *
     * @param username of the creator of the event
     * @param title
     * @param location
     * @param description
     * @param startDate
     * @param endDate
     */
    public BaseEventDTO(final String title, final LocationDTO location, final String description, final LocalDateTime startDate,
            final LocalDateTime endDate) {
        this.title = title;
        this.location = location;
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

    public LocationDTO getLocation() {
        return location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "EventCreateModel [title=" + title + ", location=" + location + ", description=" + description
                + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }


}
