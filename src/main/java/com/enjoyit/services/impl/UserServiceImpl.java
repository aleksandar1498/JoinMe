package com.enjoyit.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.enums.MsgServiceResponse;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.ServiceResponse;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final EventRepository eventRepository;

    @Autowired
    public UserServiceImpl(final UserRepository repository, final EventRepository eventRepository) {
        this.userRepo = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public ServiceResponse<UserEventDTO> disinterestEvent(final String username, final String eventId) {
        final ServiceResponse<UserEventDTO> response = new ServiceResponse<>();
        final User user = this.userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_USER_WITH_USERNAME);
            return response;
        }

        final Event event = this.eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
            return response;
        }
        this.eventRepository.disinterestEvent(user, event);

        response.setSuccessResponse();
        return response;
    }

    @Override
    public ServiceResponse disjoinEvent(final String username, final String eventId) {
        final ServiceResponse response = new ServiceResponse<>();
        final User user = this.userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_USER_WITH_USERNAME);
            return response;
        }

        final Event event = this.eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
            return response;
        }
        this.eventRepository.disjoinEvent(user, event);

        response.setSuccessResponse();
        return response;
    }

    @Override
    public UserWithEventsDTO findByUsername(final String username) {
        System.out.println("here");
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.map(u, UserWithEventsDTO.class);
        }).orElse(null);
    }

    @Override
    public List<EventDTO> getInterestedEvents(final String username) {
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.mapAll(u.getInterestedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()),EventDTO.class);
        }).orElseThrow(() -> {
            return new EntityNotFoundException("A user with this username does not exists");
        });
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.mapAll(u.getJoinedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()),EventDTO.class);
        }).orElseThrow(() -> {
            return new EntityNotFoundException("A user with this username does not exists");
        });
    }

    @Override
    public ServiceResponse<UserEventDTO> interestEvent(final String username, final String eventId) {
        final ServiceResponse<UserEventDTO> response = new ServiceResponse<>();
        final User user = this.userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_USER_WITH_USERNAME);
            return response;
        }

        final Event event = this.eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
            return response;
        }

        final EventUser eventUser = eventRepository.interestEvent(user, event);
        response.setSuccessResponse();
        response.setResponseObject(ObjectMapper.map(eventUser, UserEventDTO.class));
        return response;
    }

    @Override
    public ServiceResponse<UserEventDTO> joinEvent(final String username, final String id) {
        final ServiceResponse<UserEventDTO> response = new ServiceResponse<>();
        final User user = this.userRepo.findByUsername(username).orElse(null);

        if (user == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_USER_WITH_USERNAME);
            return response;
        }

        final Event event = this.eventRepository.findById(id).orElse(null);
        if (event == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
            return response;
        }
        final EventUser eventUser = eventRepository.joinEvent(user, event);

        response.setSuccessResponse();
        response.setResponseObject(ObjectMapper.map(eventUser, UserEventDTO.class));
        return response;
    }
}
