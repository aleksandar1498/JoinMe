package com.enjoyit.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.models.EventCreateModel;
import com.enjoyit.enums.MsgServiceResponse;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.services.ServiceResponse;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.EventDTOtoEntityCoverter;
import com.enjoyit.utils.ObjectMapper;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserService userService;
    private final EventDTOtoEntityCoverter entityConverter;

    @Autowired
    public EventServiceImpl(final EventRepository repository, final UserService userService, final ModelMapper mapper,
            final EventDTOtoEntityCoverter entityConverter) {
        this.entityConverter = entityConverter;
        this.userService = userService;
        this.eventRepo = repository;
    }

    @Override
    public ServiceResponse cancelEventById(final Integer id) {
        final ServiceResponse response = new ServiceResponse();
        final JpaEvent event = this.eventRepo.findById(id).orElse(null);
        if (event == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
            return response;
        }

        event.setCancelled();
        this.eventRepo.save(event);
        response.setSuccessResponse();
        return response;
    }

    @Override
    public ServiceResponse createEvent(final BaseEventDTO eventModel, final String username) {
        final ServiceResponse response = new ServiceResponse();
        final UserWithEventsDTO user = this.userService.findByUsername(username);
        if (user == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.setResponseMessage(MsgServiceResponse.NO_USER_WITH_USERNAME);
            return response;
        }
        final JpaEvent eventToCreate = ObjectMapper.map(eventModel, JpaEvent.class);
        ObjectMapper.addConverter(entityConverter);
        eventToCreate.setOwner(ObjectMapper.map(user, JpaUser.class));
        this.eventRepo.save(eventToCreate);
        response.setSuccessResponse();
        return response;
    }

    @Override
    public ServiceResponse editEventById(final Integer id, final EventCreateModel event) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        final List<JpaEvent> events = eventRepo.findAll();
        return events.stream().map(e -> {
            final UserDTO owner = ObjectMapper.map(e.getOwner(), UserDTO.class);
            final List<UserDTO> joinedUsers = ObjectMapper.mapAll(
                    e.getJoinedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()), UserDTO.class);
            final List<UserDTO> interestedUsers = ObjectMapper.mapAll(
                    e.getInterestedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                    UserDTO.class);
            return new EventDTO(e.getId(), e.getTitle(), e.getLocation(), e.getStartDate(), e.getEndDate(), owner,
                    e.getDescription(), e.getCancelled(), joinedUsers, interestedUsers);
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllOuterEvents(final String username) {
        return ObjectMapper.mapAll(this.eventRepo.getEventsNotBelongingTo(username), EventDTO.class);
    }

    @Override
    public Optional<EventDTO> getEventById(final Integer id) {
        final Optional<JpaEvent> event = this.eventRepo.findById(id);

        return event.map(e -> {
            System.out.println("CANCELLED " + e.getCancelled());
            final UserDTO owner = ObjectMapper.map(e.getOwner(), UserDTO.class);
            final List<UserDTO> joinedUsers = ObjectMapper.mapAll(
                    e.getJoinedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()), UserDTO.class);
            final List<UserDTO> interestedUsers = ObjectMapper.mapAll(
                    e.getInterestedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
                    UserDTO.class);
            return new EventDTO(e.getId(), e.getTitle(), e.getLocation(), e.getStartDate(), e.getEndDate(), owner,
                    e.getDescription(), e.getCancelled(), joinedUsers, interestedUsers);
        });
    }

    @Override
    public List<EventDTO> getEventByLocation(final String location) {
        return ObjectMapper.mapAll(eventRepo.findByLocation(location), EventDTO.class);
    }

    @Override
    public List<EventDTO> getEventByOwner(final String owner) {
        return ObjectMapper.mapAll(eventRepo.findByOwnerUsername(owner), EventDTO.class);
    }

    @Override
    public List<EventDTO> getJoinedEvents(final String username) {
        return null;
        // final User user = this.userRepo.findByUsername(username).orElseThrow(() -> {
        // throw new IllegalArgumentException("A user with this username does not
        // exists");
        // });
        // user.getJoinedEvents();
        // return ObjectMapper.mapAll(
        // user.getJoinedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()),
        // EventDTO.class);
    }

}
