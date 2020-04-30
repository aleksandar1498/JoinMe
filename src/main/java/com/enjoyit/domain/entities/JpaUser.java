package com.enjoyit.domain.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

@Entity
@Table(name = "users")
public class JpaUser implements UserDetails,User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String username;

    @Column
    private String password;

    /*
     * @Column private String email;
     */

    @OneToMany(mappedBy = "owner",targetEntity = JpaEvent.class)
    private List<Event> events;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<JpaRole> authorities;

    @OneToMany(mappedBy = "user",targetEntity = JpaEventUser.class)
    List<EventUser> joinedEvents;

    private boolean enabled;

    private boolean locked;

    private boolean expired;

    private boolean expiredCredentials;

    public JpaUser() {
        // needed by JPA
    }

    public JpaUser(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void addEvent(final Event event) {
        this.events.add(event);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<EventUser> getJoinedEvents() {
        return joinedEvents;
    }

    @Override
    public String getPassword() {
        return password;
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

    public void setAuthorities(final Set<JpaRole> authorities) {
        this.authorities = authorities;
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

    public void setId(final String id) {
        this.id = id;
    }

    public void setJoinedEvents(final List<EventUser> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }


}
