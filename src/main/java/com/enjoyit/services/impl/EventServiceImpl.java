package com.enjoyit.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.services.UserService;
import com.enjoyit.utils.EventDTOtoEntityCoverter;
import com.enjoyit.utils.ObjectMapper;

/**
 * @author AStefanov
 */
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserService userService;

    /**
     * @param repository
     * @param userService
     * @param mapper
     * @param eventEntityConverter
     */
    @Autowired
    public EventServiceImpl(final EventRepository repository, final UserService userService, final ModelMapper mapper,
            final EventDTOtoEntityCoverter eventEntityConverter) {
        this.userService = userService;
        this.eventRepo = repository;
        ObjectMapper.addConverter(eventEntityConverter);
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
        final UserWithEventsDTO user = this.userService.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User with this username was not found");
        }
        final JpaEvent eventToCreate = ObjectMapper.map(eventModel, JpaEvent.class);
        eventToCreate.setOwner(ObjectMapper.map(user, JpaUser.class));
        eventToCreate.setLocation(eventModel.getLocation() == null ? null
                : ObjectMapper.map(eventModel.getLocation(), JpaLocation.class));
        return ObjectMapper.map(this.eventRepo.saveAndFlush(eventToCreate), EventDTO.class);
    }

    @Override
    public EventDTO editEventById(final String id, final BaseEventDTO event) {
        final JpaEvent eventJpa = this.eventRepo.getOne(id);
        eventJpa.setDescription(event.getDescription());
        eventJpa.setEndDate(event.getEndDate());
        eventJpa.setStartDate(event.getStartDate());
        return ObjectMapper.map(this.eventRepo.save(eventJpa), EventDTO.class);
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
    public EventDTO getEventById(final String id) {
        return this.eventRepo.findById(id).map(e -> ObjectMapper.map(e, EventDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("An Event with this id was not found"));
    }

    @Override
    public List<EventDTO> getEventByLocation(final String location) {
        return ObjectMapper.mapAll(eventRepo.findByLocation(location), EventDTO.class);
    }

    @Override
    public List<EventDTO> getEventByOwner(final String owner) {
        return ObjectMapper.mapAll(eventRepo.findByOwnerUsername(owner), EventDTO.class);
    }


}
