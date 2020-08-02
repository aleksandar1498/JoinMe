package com.enjoyit.domain.dto;

import java.util.List;

public class UserWithEventsDTO extends UserDTO {
    private List<EventDTO> events;
    private List<JoinEventDTO> joinedEvents;
    private List<InterestEventDTO> interestedEvents;
    public UserWithEventsDTO() {
    }

    public UserWithEventsDTO(final String id, final String username, final String name, final String surname,
            final String email, final List<EventDTO> events) {
        super(id, username, name, surname, email);
        this.events = events;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(final List<EventDTO> events) {
        this.events = events;
    }

    public List<JoinEventDTO> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(final List<JoinEventDTO> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public List<InterestEventDTO> getInterestedEvents() {
        return interestedEvents;
    }

    public void setInterestedEvents(final List<InterestEventDTO> interestedEvents) {
        this.interestedEvents = interestedEvents;
    }

}
