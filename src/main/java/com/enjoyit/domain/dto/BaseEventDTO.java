package com.enjoyit.domain.dto;

import java.time.LocalDateTime;

public class BaseEventDTO {
    private String title;
    private String locationId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public BaseEventDTO() {
        // TODO Auto-generated constructor stub
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



    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setLocationId(final String locationId) {
        this.locationId = locationId;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BaseEventDTO [title=" + title + ", location=" + locationId + ", description=" + description
                + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }


}
