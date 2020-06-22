package com.enjoyit.services.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.enjoyit.BaseTest;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.entities.JpaEvent;
import com.enjoyit.enums.EventCategory;
import com.enjoyit.persistence.EventRepository;
import com.enjoyit.services.EventService;

class EventServiceImplTest extends BaseTest {

    private static final List<JpaEvent> EMPTY_EVENTS_LIST = new ArrayList<>();
    private static final List<JpaEvent> NOT_EMPTY_EVENTS_LIST = new ArrayList<>() {
        {
            add(new JpaEvent("Jog", "Sofia", LocalDateTime.now(), LocalDateTime.now(),EventCategory.COMEDY_EVENT,""));
        }
    };

    @Autowired
    EventService service;

    @MockBean
    EventRepository eventRepo;

    @Test
    void getAllEvents_whereEventIsPresent_shouldNotReturnAnEmptyList() {
        when(eventRepo.findAll()).thenReturn(NOT_EMPTY_EVENTS_LIST);
        final List<EventDTO> result = this.service.getAllEvents();
        assertTrue(result.get(0).getTitle().equals("Jog"));
        assertTrue(result.get(0).getLocation().equals("Sofia"));
    }

    @Test
    void testGetAllEventsWhereEventsAreNotPresentShouldReturnAnEmptyList() {
        when(eventRepo.findAll()).thenReturn(EMPTY_EVENTS_LIST);
        assertTrue(this.service.getAllEvents().isEmpty());
    }

}
