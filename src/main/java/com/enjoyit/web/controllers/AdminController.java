package com.enjoyit.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserDTO;
import com.enjoyit.services.EventService;
import com.enjoyit.services.UserService;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public AdminController(final EventService eventService, final UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PutMapping(path = "/events/ban/{id}")
    @ResponseBody
    public ResponseEntity<EventDTO> banEvent(@PathVariable("id") final String eventId) {
        this.eventService.ban(eventId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/users/ban/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> banUser(@PathVariable("id") final String userId) {
        this.userService.ban(userId);
        return ResponseEntity.noContent().build();
    }
}
