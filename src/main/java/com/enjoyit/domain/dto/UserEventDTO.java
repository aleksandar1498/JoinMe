package com.enjoyit.domain.dto;

public class UserEventDTO {
    /**
     *
     */
    UserDTO user;
    /**
     *
     */
    EventDTO event;

    public UserEventDTO() {
       this.user = null;
       this.event = null;
    }

    public UserEventDTO(final UserDTO user, final EventDTO event) {
        this.user = user;
        this.event = event;
    }

    public EventDTO getEvent() {
        return event;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setEvent(final EventDTO event) {
        this.event = event;
    }

    public void setUser(final UserDTO user) {
        this.user = user;
    }



}
