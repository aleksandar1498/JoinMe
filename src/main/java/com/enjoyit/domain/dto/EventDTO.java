package com.enjoyit.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<JoinUserDTO> joinedUsers = new ArrayList<JoinUserDTO>();
    private List<JoinUserDTO> interestedUsers = new ArrayList<JoinUserDTO>();

    public EventDTO() {
    }

    public EventDTO(final String id, final String title, final LocationDTO location, final LocalDateTime startDate,
            final LocalDateTime endDate, final String description, final Boolean cancelled,
            final List<JoinUserDTO> joinedUsers, final List<JoinUserDTO> interestedUsers) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.owner = null;
        this.joinedUsers = joinedUsers;
        this.cancelled = cancelled;
        this.interestedUsers = interestedUsers;
    }

    public EventDTO(final String id, final String title, final LocationDTO location, final LocalDateTime startDate,
            final LocalDateTime endDate, final UserDTO owner, final String description, final Boolean cancelled,
            final List<JoinUserDTO> joinedUsers, final List<JoinUserDTO> interestedUsers) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.owner = owner;
        this.joinedUsers = joinedUsers;
        this.cancelled = cancelled;
        this.interestedUsers = interestedUsers;
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

    @Override
    public String toString() {
        return "EventDTO [id=" + id + ", title=" + this.getTitle() + ", owner=" + owner + ", joinedUsers=" + joinedUsers
                + ", interestedUsers=" + interestedUsers + "]";
    }

}
