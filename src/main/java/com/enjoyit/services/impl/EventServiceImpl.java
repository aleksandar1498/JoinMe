package com.enjoyit.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.models.EventCreateModel;
import com.enjoyit.enums.MsgServiceResponse;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.services.LocationService;
import com.enjoyit.services.ServiceResponse;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.EventDTOtoEntityCoverter;
import com.enjoyit.utils.ObjectMapper;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserService userService;
    private final LocationService locationService;

    @Autowired
    public EventServiceImpl(final EventRepository repository, final UserService userService,final LocationService locationService, final ModelMapper mapper,
            final EventDTOtoEntityCoverter eventEntityConverter) {
        this.locationService = locationService;
        this.userService = userService;
        this.eventRepo = repository;
        ObjectMapper.addConverter(eventEntityConverter);
    }

    @Override
    public ServiceResponse cancelEventById(final Integer id) {
        new ServiceResponse();
        final JpaEvent event = this.eventRepo.findById(id).orElse(null);
        if (event == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND, MsgServiceResponse.NO_EVENT_WITH_ID_FOUND);
        }
        event.setCancelled();
        this.eventRepo.save(event);
        return ServiceResponse.successResponse();
    }

    @Override
    public ServiceResponse createEvent(final BaseEventDTO eventModel, final String username) {
        final UserWithEventsDTO user = this.userService.findByUsername(username);
        if (user == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND, MsgServiceResponse.NO_USER_WITH_USERNAME);
        }
        final Optional<LocationDTO> location = this.locationService.findLocationById(eventModel.getLocationId());

        final JpaEvent eventToCreate = ObjectMapper.map(eventModel, JpaEvent.class);
        eventToCreate.setOwner(ObjectMapper.map(user, JpaUser.class));
        eventToCreate.setLocation(ObjectMapper.map(location, JpaLocation.class));
        this.eventRepo.save(eventToCreate);
        return ServiceResponse.successResponse();
    }

    @Override
    public ServiceResponse editEventById(final Integer id, final EventCreateModel event) {
        // TODO Auto-generated method stub
        return null;
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
    public Optional<EventDTO> getEventById(final Integer id) {
        return null;
        // return this.eventRepo.findById(id).map(e -> {
        // final UserDTO owner = ObjectMapper.map(e.getOwner(), UserDTO.class);
        // final List<UserDTO> joinedUsers = ObjectMapper.mapAll(
        // e.getJoinedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
        // UserDTO.class);
        // final List<UserDTO> interestedUsers = ObjectMapper.mapAll(
        // e.getInterestedUsers().stream().map(EventUser::getUser).collect(Collectors.toList()),
        // UserDTO.class);
        // return new EventDTO(e.getId(), e.getTitle(), e.getLocation(),
        // e.getStartDate(), e.getEndDate(), owner,
        // e.getDescription(), e.getCancelled(), joinedUsers, interestedUsers);
        // });
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
