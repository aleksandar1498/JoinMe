package com.enjoyit.domain.dto;

import java.util.List;

public class UserWithEventsDTO extends UserDTO{
    private List<EventDTO> events;


    public UserWithEventsDTO() {
    }

    public UserWithEventsDTO(final String username, final String password,final List<EventDTO> events) {
        super(username,password);
        this.events = events;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(final List<EventDTO> events) {
        this.events = events;
    }

}
