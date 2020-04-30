package com.enjoyit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.services.EventService;

/**
 * RestController used to manage all the users requests related to the events
 */
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(final EventService service) {
        this.service = service;
    }

    @PostMapping
    public EventDTO createEvent(final HttpSession session){
        return this.service.createEvent((String) session.getAttribute("username"));
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return this.service.getAllEvents();
    }

    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/{location}")
    public List<EventDTO> getAllEventsByLocation(@PathVariable("location") final String location) {
        return this.service.getEventByLocation(location);
    }


    /**
     * @return a list with all the events, it returns an empty List if there are not
     *         events
     */
    @GetMapping("/users/{username}")
    public List<EventDTO> getAllEventsByOwnerUsername(@PathVariable("username") final String username) {
        return this.service.getEventByOwner(username);
    }

}
