package com.enjoyit.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.RoleDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.Role;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.RoleRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.ObjectMapper;

/**
 * @author AStefanov
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final EventRepository eventRepository;

    /**
     * @param repository
     * @param eventRepository
     * @param roleRepo
     */
    @Autowired
    public UserServiceImpl(final UserRepository repository, final EventRepository eventRepository,
            final RoleRepository roleRepo) {
        this.userRepo = repository;
        this.eventRepository = eventRepository;
        this.roleRepo = roleRepo;
    }

    @Override
    public void ban(final String username) {
        final JpaUser user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        user.setBanned(Boolean.TRUE);
        this.userRepo.save(user);
    }

    @Override
    public void disinterestEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        this.eventRepository.disinterestEvent(user, event);
    }

    @Override
    public void disjoinEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        this.eventRepository.disjoinEvent(user, event);
    }

    @Override
    public List<UserWithRolesDTO> findAllUsers() {
        return ObjectMapper.mapAll(this.userRepo.findAll(), UserWithRolesDTO.class);
    }

    @Override
    public UserWithEventsDTO findByUsername(final String username) {
        return this.userRepo.findByUsername(username).map(u -> ObjectMapper.map(u, UserWithEventsDTO.class))
                .orElse(null);
    }

    @Override
    public List<EventDTO> getInterestedEvents(final String username) {
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.mapAll(
                    u.getInterestedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()),
                    EventDTO.class);
        }).orElseThrow(() -> new EntityNotFoundException("A user with this username does not exists"));
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.mapAll(
                    u.getJoinedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()), EventDTO.class);
        }).orElseThrow(() -> new EntityNotFoundException("A user with this username does not exists"));
    }

    @Override
    public UserEventDTO interestEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        return ObjectMapper.map(eventRepository.interestEvent(user, event), UserEventDTO.class);
    }

    @Override
    public UserEventDTO joinEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        return ObjectMapper.map(eventRepository.joinEvent(user, event), UserEventDTO.class);
    }

    private Set<Role> mapToJpaRoleSet(final List<RoleDTO> roles) {
        final Set<Role> authorities = new HashSet<Role>();
        roles.forEach(r -> authorities.add(this.roleRepo.findByAuthority(r.getAuthority())));

        return authorities;
    }

    @Override
    public UserWithRolesDTO updateRoles(final UserWithRolesDTO userWithRoles) {
        final JpaUser user = this.userRepo.findByUsername(userWithRoles.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("A user with this username does not exists"));
        user.setAuthorities(mapToJpaRoleSet(userWithRoles.getAuthorities()));
        return ObjectMapper.map(this.userRepo.save(user), UserWithRolesDTO.class);
    }
}
