package com.enjoyit.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

@Entity
@Table(name = "events")
public class JpaEvent implements Event{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String title;

    @Column
    private String location;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = JpaUser.class)
    private User owner;

    @OneToMany(mappedBy = "event",targetEntity = JpaEventUser.class)
    List<EventUser> joinedUsers;

    public JpaEvent() {
        // TODO Auto-generated constructor stub
    }


    public JpaEvent(final String title, final String location, final LocalDateTime startDate, final LocalDateTime endDate) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = null;
    }


    public JpaEvent(final String title, final String location, final LocalDateTime startDate, final LocalDateTime endDate,
            final User owner) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
    }


    @Override
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public Integer getId() {
        return id;
    }


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

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setId(final Integer id) {
        this.id = id;
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
