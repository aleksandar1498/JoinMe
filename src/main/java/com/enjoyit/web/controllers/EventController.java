package com.enjoyit.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.services.EventService;

/**
 * RestController used to manage all the users requests related to the events
 */
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    /**
     * @param service
     */
    @Autowired
    public EventController(final EventService service) {
        this.eventService = service;
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> cancelEventById(@PathVariable("id") final String id) {
        this.eventService.cancelEventById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * @param principal
     * @param event
     * @param ucBuilder
     * @return
     */
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(final Principal principal, @RequestBody final BaseEventDTO event,final UriComponentsBuilder ucBuilder) {
        final EventDTO created = this.eventService.createEvent(event, principal.getName());
        return ResponseEntity.created(ucBuilder.path("/events/{id)").buildAndExpand(created.getId()).toUri()).build();

    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> editEventById(@PathVariable("id") final String id,
            @RequestBody final BaseEventDTO event,final UriComponentsBuilder ucBuilder) {
        final EventDTO edited = this.eventService.editEventById(id, event);
        return ResponseEntity.created(ucBuilder.path("/events/{id)").buildAndExpand(edited.getId()).toUri()).build();
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping
    public  ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(this.eventService.getAllEvents());
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/loc/{location}")
    public  ResponseEntity<List<EventDTO>> getAllEventsByLocation(@PathVariable("location") final String location) {
        return ResponseEntity.ok(this.eventService.getEventByLocation(location));
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/user")
    public  ResponseEntity<List<EventDTO>> getAllEventsByOwnerUsername(final Principal principal) {
        return ResponseEntity.ok(this.eventService.getEventByOwner(principal.getName()));
    }

    /**
     * @return a list with all the not created by the logged user
     *         events
     */
    @GetMapping("/others")
    public ResponseEntity<List<EventDTO>> getAllOuterEvents(final Principal principal) {
        return ResponseEntity.ok(this.eventService.getAllOuterEvents(principal.getName()));
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") final String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }


}
