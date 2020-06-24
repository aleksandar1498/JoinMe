package com.enjoyit.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserDTO;
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
    public ServiceResponse<UserEventDTO> disinterestEvent(final String username, final int eventId) {
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
    public ServiceResponse disjoinEvent(final String username, final int eventId) {
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
        return this.userRepo.findByUsername(username).map(u -> {
            return ObjectMapper.map(u, UserWithEventsDTO.class);
        }).orElse(null);
    }

    @Override
    public List<EventDTO> getInterestedEvents(final String username) {
        final User user = this.userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException(MsgServiceResponse.NO_USER_WITH_USERNAME.toString());
        }
        return user.getInterestedEvents().stream().map(ev -> {
            final Event event = ev.getEvent();

            return new EventDTO(event.getId(), event.getTitle(), event.getLocation(), event.getStartDate(),
                    event.getEndDate(), ObjectMapper.map(event.getOwner(), UserDTO.class), event.getDescription(),
                    event.getCancelled(),
                    ObjectMapper.mapAll(
                            event.getJoinedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                            UserDTO.class),
                    ObjectMapper.mapAll(
                            event.getInterestedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                            UserDTO.class));
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {

        final User user = this.userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException(MsgServiceResponse.NO_USER_WITH_USERNAME.toString());
        }
        return user.getJoinedEvents().stream().map(ev -> {
            final Event event = ev.getEvent();

            return new EventDTO(event.getId(), event.getTitle(), event.getLocation(), event.getStartDate(),
                    event.getEndDate(), ObjectMapper.map(event.getOwner(), UserDTO.class), event.getDescription(),
                    event.getCancelled(),
                    ObjectMapper.mapAll(
                            event.getJoinedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                            UserDTO.class),
                    ObjectMapper.mapAll(
                            event.getInterestedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                            UserDTO.class));
        }).collect(Collectors.toList());
    }

    @Override
    public ServiceResponse<UserEventDTO> interestEvent(final String username, final int eventId) {
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
    public ServiceResponse<UserEventDTO> joinEvent(final String username, final int id) {
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
        System.out.println(user.getUsername());
        System.out.println(event.getTitle());
        final EventUser eventUser = eventRepository.joinEvent(user, event);

        response.setSuccessResponse();
        response.setResponseObject(ObjectMapper.map(eventUser, UserEventDTO.class));
        return response;
    }
}
