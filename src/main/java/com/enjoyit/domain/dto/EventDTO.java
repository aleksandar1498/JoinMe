package com.enjoyit.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDTO extends BaseEventDTO{
    private Integer id;
    private UserDTO owner;
    private Boolean cancelled;
    private List<JoinUserDTO> joinedUsers = new ArrayList<JoinUserDTO>();
    private List<JoinUserDTO> interestedUsers = new ArrayList<JoinUserDTO>();

    public EventDTO() {
        super();
    }

    public EventDTO(final Integer id,final String title, final LocationDTO location, final LocalDateTime startDate, final LocalDateTime endDate,final String description,final Boolean cancelled,final List<JoinUserDTO> joinedUsers,final List<JoinUserDTO> interestedUsers) {
       super(title,location,description,startDate,endDate);
       this.id = id;
        this.owner = null;
        this.joinedUsers = joinedUsers;
        this.cancelled = cancelled;
        this.interestedUsers = interestedUsers;
    }

    public EventDTO(final Integer id,final String title, final LocationDTO location, final LocalDateTime startDate, final LocalDateTime endDate,final UserDTO owner,final String description,final Boolean cancelled,final List<JoinUserDTO> joinedUsers,final List<JoinUserDTO> interestedUsers) {

        super(title,location,description,startDate,endDate);
        this.id = id;
        this.owner = owner;
        this.joinedUsers = joinedUsers;
        this.cancelled = cancelled;
        this.interestedUsers = interestedUsers;
    }


    public Boolean getCancelled() {
        return cancelled;
    }
    public Integer getId() {
        return id;
    }

    public List<JoinUserDTO> getInterestedUsers() {
        return interestedUsers;
    }

    public List<JoinUserDTO> getJoinedUsers() {
        return joinedUsers;
    }


    public UserDTO getOwner() {
        return owner;
    }

    public void setCancelled(final Boolean cancelled) {
        this.cancelled = cancelled;
    }
    public void setId(final Integer id) {
        this.id = id;
    }

    public void setInterestedUsers(final List<JoinUserDTO> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public void setJoinedUsers(final List<JoinUserDTO> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }


    public void setOwner(final UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "EventDTO [id=" + id + ", title=" + super.getTitle() + ", owner=" + owner + ", joinedUsers=" + joinedUsers
                + ", interestedUsers=" + interestedUsers + "]";
    }








}
