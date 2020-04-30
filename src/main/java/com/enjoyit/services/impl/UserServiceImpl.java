package com.enjoyit.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventRepository;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.UserRepository;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EventRepository eventRepository;

    @Autowired
    public UserServiceImpl(final UserRepository repository,
            final EventRepository eventRepository) {
        this.userRepo = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public UserWithEventsDTO findByUsername(final String username) {
        return ObjectMapper.map(this.userRepo.findJpaUserByUsername(username), UserWithEventsDTO.class);
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {
        return ObjectMapper.mapAll(this.eventRepository.getJoinedEvents(username), EventDTO.class);
    }

    @Override
    public UserEventDTO joinEvent(final String username, final int id) {
        final User user = this.userRepo.findJpaUserByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException("A user with this username does not exists");
        });

        final Event event = this.eventRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Event not found");
        });
        final EventUser eventUser = eventRepository.joinEvent(user, event);
        return ObjectMapper.map(eventUser, UserEventDTO.class);
    }
}
