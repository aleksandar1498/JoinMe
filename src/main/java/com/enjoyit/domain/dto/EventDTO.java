package com.enjoyit.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.enjoyit.enums.EventCategory;

public class EventDTO {
    private String id;
    private String title;
    private LocationDTO location;
    private String description;
    private EventCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserDTO owner;
    private Boolean cancelled;
    private Boolean banned;
    private List<JoinUserDTO> joinedUsers;
    private List<JoinUserDTO> interestedUsers;

    public EventDTO() {
    }


    public EventDTO(final String title, final LocationDTO location, final String description, final EventCategory category) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.category = category;
    }


    public Boolean getBanned() {
        return banned;
    }

    public Boolean getCancelled() {
        return cancelled;
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

    public String getId() {
        return id;
    }

    public List<JoinUserDTO> getInterestedUsers() {
        return interestedUsers;
    }

    public List<JoinUserDTO> getJoinedUsers() {
        return joinedUsers;
    }

    public LocationDTO getLocation() {
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

    public void setBanned(final Boolean banned) {
        this.banned = banned;
    }

    public void setCancelled(final Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setCategory(final EventCategory category) {
        this.category = category;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setInterestedUsers(final List<JoinUserDTO> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public void setJoinedUsers(final List<JoinUserDTO> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public void setLocation(final LocationDTO location) {
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
