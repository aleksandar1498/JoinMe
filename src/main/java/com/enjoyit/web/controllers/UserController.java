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

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
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
    public ResponseEntity<UserEventDTO> disinterestEvent(@PathVariable("id") final String id,
            final Principal principal) {
        this.userService.disinterestEvent(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disjoin/{id}")
    public ResponseEntity<UserEventDTO> disjoinEvent(@PathVariable("id") final String id, final Principal principal) {
        this.userService.disjoinEvent(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserWithRolesDTO>> findAllUsers(){
        return ResponseEntity.ok(this.userService.findAllUsers());
    }

    @GetMapping("/events/interested")
    public ResponseEntity<List<EventDTO>> getInterestedEvents(final Principal principal) {
        return ResponseEntity.ok(this.userService.getInterestedEvents(principal.getName()));
    }

    @GetMapping("/events/joined")
    public ResponseEntity<List<EventDTO>> getJoinedEvents(final Principal principal) {
        return ResponseEntity.ok(this.userService.getJoinedEvents(principal.getName()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserWithEventsDTO> getUser(@PathVariable("username") final String username) {
        return ResponseEntity.ok(this.userService.findByUsername(username));
    }

    @PostMapping("/interest/{id}")
    public ResponseEntity<UserEventDTO> interestEvent(@PathVariable("id") final String id, final Principal principal) {
        return ResponseEntity.ok(this.userService.interestEvent(principal.getName(), id));
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<UserEventDTO> joinEvent(@PathVariable("id") final String id, final Principal principal) {
        return ResponseEntity.ok(this.userService.joinEvent(principal.getName(), id));
    }

    @PutMapping("/roles")
    public ResponseEntity<UserWithRolesDTO> updateRoles(final Principal principal,@RequestBody final UserWithRolesDTO user) {
        if(principal.getName().equals(user.getUsername())) {
            throw new IllegalArgumentException("You are not authorised to change your own roles     ");
        }
        return ResponseEntity.ok(this.userService.updateRoles(user));
    }
}
