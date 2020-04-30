package com.enjoyit.domain.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private String title;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserDTO owner;

    public EventDTO() {
        // Needed for reflection
    }

    public EventDTO(final String title, final String location, final LocalDateTime startDate, final LocalDateTime endDate) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate =  endDate;
        this.owner = null;
    }

    public EventDTO(final String title, final String location, final LocalDateTime startDate, final LocalDateTime endDate,final UserDTO owner) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate =  endDate;
        this.owner = owner;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }
    public String getLocation() {
        return location;
    }
    public UserDTO getOwner() {
        return owner;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setOwner(final UserDTO owner) {
        this.owner = owner;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setTitle(final String title) {
        this.title = title;
    }




}
