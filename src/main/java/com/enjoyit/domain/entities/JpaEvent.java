package com.enjoyit.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import com.enjoyit.enums.EventCategory;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

@Entity
@Table(name = "events")
@NamedQuery(name = JpaEvent.EVENTS_NOT_BELONGING_TO_USERNAME,
        query = "SELECT e FROM JpaEvent e  WHERE e.owner.username <> :username")

public class JpaEvent implements Event {
    public static final String EVENTS_NOT_BELONGING_TO_USERNAME = "eventsNotBelongingTo";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Column
    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @Column
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @Column
    @FutureOrPresent(message = "StartDate cannot be in the past")
    private LocalDateTime startDate;

    @Column
    @FutureOrPresent(message = "EndDate cannot be in the past")
    private LocalDateTime endDate;

    @Column
    private Boolean cancelled = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = JpaUser.class, cascade = CascadeType.ALL)
    private User owner;

    @OneToMany(mappedBy = "event", targetEntity = JpaUserJoinEvent.class)
    private List<EventUser> joinedUsers;

    @OneToMany(mappedBy = "event", targetEntity = JpaUserInterestEvent.class)
    private List<EventUser> interestedUsers;

    public JpaEvent() {
        // Needed by JPA
    }

    public JpaEvent(final String title, final String location, final LocalDateTime startDate,
            final LocalDateTime endDate, final EventCategory category, final String description) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
        this.owner = null;
    }

    public JpaEvent(final String title, final String location, final LocalDateTime startDate,
            final LocalDateTime endDate, final User owner, final EventCategory category, final String description) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.description = description;
        this.category = category;
    }

    @Override
    public Boolean getCancelled() {
        return cancelled;
    }

    public EventCategory getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public List<EventUser> getInterestedUsers() {
        return interestedUsers;
    }

    public List<User> getJoined() {
        return joinedUsers.stream().map(EventUser::getUser).collect(Collectors.toList());
    }

    @Override
    public List<EventUser> getJoinedUsers() {
        return joinedUsers;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setCancelled() {
       this.cancelled = Boolean.TRUE;
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

    public void setId(final int id) {
        this.id = id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setInterestedUsers(final List<EventUser> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public void setJoinedUsers(final List<EventUser> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setTitle(final String title) {
        this.title = title;
    }



}
