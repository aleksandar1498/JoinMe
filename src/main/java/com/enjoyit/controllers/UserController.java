package com.enjoyit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/events/joined")
    public List<EventDTO> getJoinedEvents(final HttpSession session) {
        final String username = (String) session.getAttribute("username");
        return this.userService.getJoinedEvents(username);
    }

    @GetMapping("/{username}")
    public UserWithEventsDTO getUser(@PathVariable("username") final String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("/join/{id}")
    public UserEventDTO joinEvent(@PathVariable("id") final String id, final HttpSession session) {
        final String username = (String) session.getAttribute("username");
        return this.userService.joinEvent(username, Integer.parseInt(id));
    }
}
