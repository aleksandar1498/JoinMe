package com.enjoyit.persistence.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.Notification;
import com.enjoyit.persistence.Role;
import com.enjoyit.persistence.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author AStefanov
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class JpaUser extends BaseEntity implements UserDetails, User {


    @Column(name = "username")
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String password;


    @OneToMany(mappedBy = "owner", targetEntity = JpaEvent.class)
    private List<Event> events;

    @OneToMany(mappedBy = "recipient", targetEntity = JpaNotification.class,cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = JpaRole.class)
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> authorities;

    @OneToMany( mappedBy = "user", targetEntity = JpaUserJoinEvent.class, cascade = CascadeType.ALL)
    private List<EventUser> joinedEvents;

    @OneToMany( mappedBy = "user", targetEntity = JpaUserInterestEvent.class, cascade = CascadeType.ALL)
    private List<EventUser> interestedEvents;

    private boolean enabled;

    @Column
    private Boolean banned;

    private boolean locked;

    private boolean expired;

    private boolean expiredCredentials;

    public JpaUser() {
        // needed by JPA
    }

    public JpaUser(final String username, final String name, final String surname, final String email, final Boolean banned) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.banned = banned;
    }


    public JpaUser(final String username, final String name, final String surname, final String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.banned = Boolean.FALSE;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(a -> {
            return new SimpleGrantedAuthority("ROLE_"+a.getAuthority());
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean getBanned() {
        return banned;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public List<EventUser> getInterestedEvents() {
        return interestedEvents;
    }

    @Override
    public List<EventUser> getJoinedEvents() {
        return joinedEvents;
    }

    @Override
    public String getName() {
        return name;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.expiredCredentials;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public boolean isExpiredCredentials() {
        return expiredCredentials;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setAuthorities(final Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setBanned(final boolean banned) {
        this.banned = banned;
    }

    public void setBanned(final Boolean state) {
        this.banned = state;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setEvents(final List<Event> events) {
        this.events = events;
    }

    public void setExpired(final boolean expired) {
        this.expired = expired;
    }

    public void setExpiredCredentials(final boolean expiredCredentials) {
        this.expiredCredentials = expiredCredentials;
    }

    public void setInterestedEvents(final List<EventUser> interestedEvents) {
        this.interestedEvents = interestedEvents;
    }

    public void setJoinedEvents(final List<EventUser> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public void setId(final String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }




}
