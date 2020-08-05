package com.enjoyit.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.persistence.entities.stats.UserEventStatistic;
import com.enjoyit.services.EventService;

@RestController
@RequestMapping("/stat")
public class StatisticController {

    private final EventService eventService;

    @Autowired
    public StatisticController(final EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/user/{id}/events")
    public ResponseEntity<List<UserEventStatistic>> getEventsPerUser(@PathVariable("id") final String id) {
        return ResponseEntity.ok(this.eventService.getEventStatisticByOwnerId(id));

    }
}
