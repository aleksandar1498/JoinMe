package com.enjoyit.web.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.models.EventCreateModel;
import com.enjoyit.services.EventService;
import com.enjoyit.services.ServiceResponse;

/**
 * RestController used to manage all the users requests related to the events
 */
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(final EventService service) {
        this.eventService = service;
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @DeleteMapping("/cancel/{id}")
    public ServiceResponse cancelEventById(@PathVariable("id") final Integer id ) {
        return this.eventService.cancelEventById(id);
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @PutMapping("/edit/{id}")
    public ServiceResponse cancelEventById(@PathVariable("id") final Integer id,@RequestBody final EventCreateModel event ) {
        return this.eventService.editEventById(id,event);
    }

    @PostMapping
    public ServiceResponse createEvent(final Principal principal,@RequestBody final EventCreateModel event) {
        return this.eventService.createEvent(event,principal.getName());
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return this.eventService.getAllEvents();
    }


    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/loc/{location}")
    public List<EventDTO> getAllEventsByLocation(@PathVariable("location") final String location) {
        return this.eventService.getEventByLocation(location);
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/user")
    public List<EventDTO> getAllEventsByOwnerUsername(final Principal principal) {
        return this.eventService.getEventByOwner(principal.getName());
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/others")
    public List<EventDTO> getAllOuterEvents(final Principal principal) {
        return this.eventService.getAllOuterEvents(principal.getName());
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/{id}")
    public Optional<EventDTO> getEventById(@PathVariable("id") final Integer id ) {
        return this.eventService.getEventById(id);
    }

}
