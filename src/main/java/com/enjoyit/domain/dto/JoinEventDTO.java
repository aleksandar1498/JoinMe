package com.enjoyit.domain.dto;

public class JoinEventDTO {
    /**
     *
     */
    private UserDTO user;
    /**
     *
     */
    private EventDTO event;

    public JoinEventDTO() {
    }

    public JoinEventDTO(final UserDTO user, final EventDTO event) {
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
