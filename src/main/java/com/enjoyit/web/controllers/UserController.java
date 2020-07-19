package com.enjoyit.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.services.ServiceResponse;
import com.enjoyit.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }
    @DeleteMapping("/disinterest/{id}")
    public ServiceResponse<UserEventDTO> disinterestEvent(@PathVariable("id") final String id,
            final Principal principal) {
        return this.userService.disinterestEvent(principal.getName(), id);
    }

    @DeleteMapping("/disjoin/{id}")
    public ServiceResponse<UserEventDTO> disjoinEvent(@PathVariable("id") final String id, final Principal principal) {
        return this.userService.disjoinEvent(principal.getName(), id);
    }

    @GetMapping
    public List<UserWithRolesDTO> findAllUsers(){
        return this.userService.findAllUsers();
    }

    @GetMapping("/events/interested")
    public List<EventDTO> getInterestedEvents(final Principal principal) {
        return this.userService.getInterestedEvents(principal.getName());
    }

    @GetMapping("/events/joined")
    public List<EventDTO> getJoinedEvents(final Principal principal) {
        return this.userService.getJoinedEvents(principal.getName());
    }

    @GetMapping("/{username}")
    public UserWithEventsDTO getUser(@PathVariable("username") final String username) {
        return this.userService.findByUsername(username);
    }

    @PostMapping("/interest/{id}")
    public ServiceResponse<UserEventDTO> interestEvent(@PathVariable("id") final String id, final Principal principal) {
        return this.userService.interestEvent(principal.getName(), id);
    }

    @PostMapping("/join/{id}")
    public ServiceResponse<UserEventDTO> joinEvent(@PathVariable("id") final String id, final Principal principal) {
        return this.userService.joinEvent(principal.getName(), id);
    }

    @PutMapping("/roles")
    public ServiceResponse updateRoles(@RequestBody final UserWithRolesDTO user) {
        System.out.println(user);
        return this.userService.updateRoles(user);
    }
}
