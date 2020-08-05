package com.enjoyit.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.enjoyit.enums.EventCategory;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.Location;
import com.enjoyit.persistence.User;

@Entity
@Table(name = "events")
@NamedQuery(name = JpaEvent.EVENTS_NOT_BELONGING_TO_USERNAME,
query = "SELECT e FROM JpaEvent e  WHERE e.owner.username <> :username")
@NamedQuery(name = JpaEvent.CLEAR_EXPIRED,
query = "UPDATE JpaEvent e SET e.cancelled = 1 WHERE e.endDate < now() AND e.cancelled = 0")
@NamedQuery(name = JpaEvent.EVENTS_FOR_USER_STATISTIC,
query = "SELECT new com.enjoyit.persistence.entities.stats.UserEventStatistic(YEAR(e.startDate),MONTHNAME(e.startDate),COUNT(DISTINCT e.id)) FROM JpaEvent e WHERE e.owner.id=:id GROUP BY YEAR(e.startDate),MONTH(e.startDate) ORDER BY YEAR(e.startDate),MONTH(e.startDate) ASC ")
public class JpaEvent extends BaseEntity implements Event {

    public static final String EVENTS_NOT_BELONGING_TO_USERNAME = "eventsNotBelongingTo";

    public static final String CLEAR_EXPIRED = "clearExpired";

    public static final String EVENTS_FOR_USER_STATISTIC = "eventsStatistic";
    @Column
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @ManyToOne(targetEntity = JpaLocation.class)
    private Location location;

    @Column
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @Column
    @NotNull(message = "StartDate cannot be null")
    private LocalDateTime startDate;

    @Column
    @NotNull(message = "EndDate cannot be null")
    private LocalDateTime endDate;

    @Column
    private Boolean cancelled = Boolean.FALSE;

    @Column
    private Boolean banned = Boolean.FALSE;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = JpaUser.class)
    private User owner;

    @OneToMany(mappedBy = "event", targetEntity = JpaUserJoinEvent.class, cascade = CascadeType.ALL)
    private List<EventUser> joinedUsers;

    @OneToMany(mappedBy = "event", targetEntity = JpaUserInterestEvent.class, cascade = CascadeType.ALL)
    private List<EventUser> interestedUsers;

    public JpaEvent() {
        // Needed by JPA
    }

    public JpaEvent(@NotEmpty(message = "Title cannot be empty") final String title, final Location location,
            @NotEmpty(message = "Description cannot be empty") final String description,
            @NotNull(message = "StartDate cannot be null") @FutureOrPresent(
                    message = "StartDate cannot be in the past") final LocalDateTime startDate,
            @NotNull(message = "EndDate cannot be null") @FutureOrPresent(
                    message = "EndDate cannot be in the past") final LocalDateTime endDate,
            final Boolean cancelled, final Boolean banned, @NotNull final EventCategory category, final User owner) {

        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancelled = cancelled;
        this.banned = banned;
        this.category = category;
        this.owner = owner;
    }

    @Override
    public Boolean getBanned() {
        return banned;
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
    public List<EventUser> getInterestedUsers() {
        return interestedUsers;
    }

    @Override
    public List<EventUser> getJoinedUsers() {
        return joinedUsers;
    }

    @Override
    public Location getLocation() {
        return this.location;
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
    public void setBanned(final Boolean banned) {
        this.banned = banned;
    }

    @Override
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

    public void setInterestedUsers(final List<EventUser> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public void setJoinedUsers(final List<EventUser> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    @Override
    public void setLocation(final Location location) {
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
