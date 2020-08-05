package com.enjoyit.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.entities.stats.UserEventStatistic;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.services.LocationService;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.EventDTOtoEntityCoverter;
import com.enjoyit.utils.ObjectMapper;
import com.enjoyit.utils.UserInterestEventDTOtoEntityConverter;
import com.enjoyit.utils.UserJoinEventDTOtoEntityConverter;

/**
 * @author AStefanov
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserService userService;
    private final LocationService locationService;
    /**
     * @param repository
     * @param userService
     * @param mapper
     * @param eventEntityConverter
     */
    @Autowired
    public EventServiceImpl(
            final EventRepository repository, final UserService userService, final ModelMapper mapper,
            final EventDTOtoEntityCoverter eventEntityConverter,final UserInterestEventDTOtoEntityConverter interestToEntityConverter,final UserJoinEventDTOtoEntityConverter joinToEntityConverter,final LocationService locationService) {
        this.userService = userService;
        this.locationService = locationService;
        this.eventRepo = repository;
        ObjectMapper.addConverter(eventEntityConverter);
        ObjectMapper.addConverter(interestToEntityConverter);
        ObjectMapper.addConverter(joinToEntityConverter);
    }

    @Override
    public void ban(final String eventId) {
        final JpaEvent event = this.eventRepo.getOne(eventId);
        event.setBanned(Boolean.TRUE);
        this.eventRepo.save(event);
    }

    @Override
    public void cancelEventById(final String id) {
        final JpaEvent event = this.eventRepo.getOne(id);
        event.setCancelled(Boolean.TRUE);
        this.eventRepo.save(event);
    }

    @Override
    public int cleanUpExpiredEvents() {
        return this.eventRepo.cancelExpired();
    }

    @Override
    public EventDTO createEvent(@Validated final BaseEventDTO eventModel, final String username) {
        System.err.println("A");
        final UserWithEventsDTO user = this.userService.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User with this username was not found");
        }
        System.err.println("B");
        final JpaEvent eventToCreate = ObjectMapper.map(eventModel, JpaEvent.class);
        eventToCreate.setOwner(ObjectMapper.map(user, JpaUser.class));
        eventToCreate.setLocation(eventModel.getLocation() == null ? null
                : ObjectMapper.map(eventModel.getLocation(), JpaLocation.class));
        System.err.println("C");
        return ObjectMapper.map(this.eventRepo.saveAndFlush(eventToCreate), EventDTO.class);
    }

    @Override
    public EventDTO editEventById(final String id, final BaseEventDTO event) {
        final JpaEvent eventJpa = this.eventRepo.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Event not found");
        });
        eventJpa.setDescription(event.getDescription());
        eventJpa.setEndDate(event.getEndDate());
        eventJpa.setStartDate(event.getStartDate());
        return ObjectMapper.map(this.eventRepo.save(eventJpa), EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        final List<JpaEvent> events = eventRepo.findAll();
        return ObjectMapper.mapAll(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllOuterEvents(final String username) {
        return ObjectMapper.mapAll(this.eventRepo.getEventsNotBelongingTo(username), EventDTO.class);
    }

    @Override
    public EventDTO getEventById(final String id) {
        return this.eventRepo.findById(id).map(e -> ObjectMapper.map(e, EventDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("An Event with this id was not found"));
    }

    @Override
    public List<EventDTO> getEventByLocation(final String locationId) {
        final LocationDTO location = this.locationService.findLocationById(locationId).orElseThrow(() -> {
            return new EntityNotFoundException("Location was not found");
        });
        return ObjectMapper.mapAll(eventRepo.findByLocation(ObjectMapper.map(location,JpaLocation.class)), EventDTO.class);
    }

    @Override
    public List<EventDTO> getEventByOwner(final String owner) {
        final UserWithEventsDTO user = this.userService.findByUsername(owner);
        if (user == null) {
            throw new EntityNotFoundException("User with this username was not found");
        }
        return ObjectMapper.mapAll(eventRepo.findByOwnerUsername(owner), EventDTO.class);
    }

    @Override
    public List<UserEventStatistic> getEventStatisticByOwnerId(final String id) {
        return this.eventRepo.getEventsStatistic(id);
    }

}
