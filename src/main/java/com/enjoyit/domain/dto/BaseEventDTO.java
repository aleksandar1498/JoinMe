package com.enjoyit.domain.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.enjoyit.enums.EventCategory;

public class BaseEventDTO {
    @NotEmpty(message = "*title cannot be empty")
    private String title;
    @NotNull
    private LocationDTO location;
    @NotEmpty(message = "*description cannot be empty")
    private String description;
    @NotNull
    private EventCategory category;
    @NotNull(message = "*startDate cannot be null")
    @FutureOrPresent(message = "*startDate cannot be in the past")
    private LocalDateTime startDate;

    @NotNull(message = "*endDate cannot be null")
    @FutureOrPresent(message = "*endDate cannot be in the past")
    private LocalDateTime endDate;

    public BaseEventDTO() {
    }

    /**
     * @param username
     *            of the creator of the event
     * @param title
     * @param location
     * @param description
     * @param startDate
     * @param endDate
     */
    public BaseEventDTO(final String title, final LocationDTO location, final String description,
            final LocalDateTime startDate, final LocalDateTime endDate,final EventCategory category) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    public EventCategory getCategory() {
        return category;
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



    public void setTitle(final String title) {
        this.title = title;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCategory(final EventCategory category) {
        this.category = category;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BaseEventDTO [title=" + title + ", location=" + location + ", description=" + description
                + ", category=" + category + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
