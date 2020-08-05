package com.enjoyit.services.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.InterestEventDTO;
import com.enjoyit.domain.dto.JoinEventDTO;
import com.enjoyit.domain.dto.NotificationDTO;
import com.enjoyit.domain.dto.RoleDTO;
import com.enjoyit.domain.dto.UserDTO;
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
import com.enjoyit.services.NotificationService;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.ObjectMapper;

/**
 * @author AStefanov
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    /**
     * @param repository
     * @param eventRepository
     * @param roleRepo
     */
    @Autowired
    public UserServiceImpl(final UserRepository repository, final EventRepository eventRepository,
            final RoleRepository roleRepo,final NotificationService notificationService) {
        this.userRepo = repository;
        this.eventRepository = eventRepository;
        this.roleRepo = roleRepo;
        this.notificationService = notificationService;
    }

    @Override
    public void authorize(final String username) {
        final JpaUser user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        user.setBanned(Boolean.FALSE);
        this.userRepo.save(user);
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
            System.out.println(u.getInterestedEvents()+" interested");
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
    public InterestEventDTO interestEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        return ObjectMapper.map(eventRepository.interestEvent(user, event), InterestEventDTO.class);
    }

    @Override
    public JoinEventDTO joinEvent(final String username, final String eventId) {
        final User user = this.userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("An user with this username does not exist"));
        final Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("An event with this username does not exist"));
        return ObjectMapper.map(eventRepository.joinEvent(user, event), JoinEventDTO.class);
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<UserDTO> updateRoles(final UserWithRolesDTO userWithRoles) {
        System.err.println(userWithRoles.getUsername());
        JpaUser user = this.userRepo.findByUsername(userWithRoles.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("A user with this username does not exists"));
        user.setAuthorities(mapToJpaRoleSet(userWithRoles.getAuthorities()));

        user = this.userRepo.save(user);
        System.out.println(ObjectMapper.map(user, UserDTO.class));
        this.notificationService.createNotification(new NotificationDTO(String.format("Your roles had been changed ad %s", LocalDateTime.now()),ObjectMapper.map(user, UserDTO.class)));
        return CompletableFuture.completedFuture(ObjectMapper.map(user, UserDTO.class));
    }


    private Set<Role> mapToJpaRoleSet(final List<RoleDTO> roles) {
        roles.forEach(r -> {
            System.out.println(r.getAuthority());
        });
        final Set<Role> authorities = new HashSet<Role>();
        roles.forEach(r -> authorities.add(this.roleRepo.findByAuthority(r.getAuthority())));

        return authorities;
    }

}
