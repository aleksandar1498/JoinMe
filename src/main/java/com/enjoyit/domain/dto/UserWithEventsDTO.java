package com.enjoyit.domain.dto;

import java.util.List;

public class UserWithEventsDTO extends UserDTO{
    private List<EventDTO> events;

    public UserWithEventsDTO() {
    }

    public UserWithEventsDTO(final String id,final String username,final List<EventDTO> events) {
        super(id,username);
        this.events = events;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(final List<EventDTO> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return super.toString()+" UserWithEventsDTO [events=" + events + "]";
    }



}
