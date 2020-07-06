package com.enjoyit.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.enums.MsgServiceResponse;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
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

    @Autowired
    public EventServiceImpl(final EventRepository repository, final UserService userService, final ModelMapper mapper,
            final EventDTOtoEntityCoverter eventEntityConverter) {
        this.userService = userService;
        this.eventRepo = repository;
        ObjectMapper.addConverter(eventEntityConverter);
    }

    @Override
    public ServiceResponse cancelEventById(final String id) {
        final JpaEvent event = this.eventRepo.findById(id).orElse(null);
        if (event == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND, MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
        }
        event.setCancelled();
        this.eventRepo.save(event);
        return ServiceResponse.successResponse();
    }

    @Override
    public ServiceResponse createEvent(@Validated final BaseEventDTO eventModel, final String username) {
        final UserWithEventsDTO user = this.userService.findByUsername(username);
        if (user == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND, MsgServiceResponse.NO_USER_WITH_USERNAME);
        }
        final JpaEvent eventToCreate = ObjectMapper.map(eventModel, JpaEvent.class);
        eventToCreate.setOwner(ObjectMapper.map(user, JpaUser.class));
        eventToCreate.setLocation(eventModel.getLocation() == null ? null
                : ObjectMapper.map(eventModel.getLocation(), JpaLocation.class));
        this.eventRepo.saveAndFlush(eventToCreate);
        return ServiceResponse.successResponse();
    }

    @Override
    public ServiceResponse editEventById(final String id, final BaseEventDTO event) {
        final JpaEvent eventJpa = this.eventRepo.getOne(id);
        eventJpa.setDescription(event.getDescription());
        eventJpa.setEndDate(event.getEndDate());
        eventJpa.setStartDate(event.getStartDate());
        this.eventRepo.save(eventJpa);
        return ServiceResponse.successResponse();
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return ObjectMapper.mapAll(eventRepo.findAll(), EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllOuterEvents(final String username) {
        return ObjectMapper.mapAll(this.eventRepo.getEventsNotBelongingTo(username), EventDTO.class);
    }

    @Override
    public Optional<EventDTO> getEventById(final String id) {
        return Optional.ofNullable(this.eventRepo.findById(id).map(e -> {
            return ObjectMapper.map(e, EventDTO.class);
        }).orElse(null));
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
        //
        // final User user = this.userService.findByUsername(username)
        // user.getJoinedEvents();
        // return ObjectMapper.mapAll(
        // user.getJoinedEvents().stream().map(EventUser::getEvent).collect(Collectors.toList()),
        // EventDTO.class);
    }

}
