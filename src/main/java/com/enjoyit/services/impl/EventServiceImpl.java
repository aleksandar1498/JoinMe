package com.enjoyit.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventRepository;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.UserRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final UserRepository userRepo;

    public EventServiceImpl(final EventRepository repository, final UserRepository userRepo) {
        this.userRepo = userRepo;
        this.repository = repository;
    }

    @Override
    public EventDTO createEvent(final String username) {
        final User user = this.userRepo.findJpaUserByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException("A user with this username does not exists");
        });
        final Event event = this.repository.createEvent("faniiito", "provasa", LocalDateTime.now(),
                LocalDateTime.now().plusDays(19), user);
        return ObjectMapper.map(event, EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return ObjectMapper.mapAll(repository.findAll(), EventDTO.class);
    }

    @Override
    public List<EventDTO> getEventByLocation(final String location) {
        return ObjectMapper.mapAll(repository.findByLocation(location), EventDTO.class);
    }

    @Override
    public List<EventDTO> getEventByOwner(final String owner) {
        return ObjectMapper.mapAll(repository.findByOwnerUsername(owner), EventDTO.class);
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {
        final User user = this.userRepo.findJpaUserByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException("A user with this username does not exists");
        });
        return ObjectMapper.mapAll(user.getJoinedEvents().stream().map(e -> {
            return e.getEvent();
        }).collect(Collectors.toList()), EventDTO.class);
    }

}
