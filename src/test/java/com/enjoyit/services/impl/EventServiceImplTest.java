package com.enjoyit.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.enums.EventCategory;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.EventService;
import com.enjoyit.utils.ObjectMapper;

@SpringBootTest
class EventServiceImplTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EventRepository eventRepository;

    private final List<JpaUser> users = new ArrayList<JpaUser>();
    private final List<JpaEvent> events = new ArrayList<JpaEvent>();

    @BeforeEach
    public void setUp() {
        this.cleanUp();
        final JpaUser alex = this.userRepository
                .save(new JpaUser("alex123", "Aleksandar", "Stefanov", "aleksandar@gmail.com", Boolean.FALSE));
        final JpaUser fani = this.userRepository
                .save(new JpaUser("fani123", "Fani", "Ah", "fani@gmail.com", Boolean.FALSE));
        final JpaLocation sofia = this.locationRepository

                .save(new JpaLocation("Ploshtad Aleko", "Sofia", LocationCategory.BAR));
        final JpaLocation province = this.locationRepository
                .save(new JpaLocation("Ticha", "Somewhere in the province", LocationCategory.OTHER));

        final JpaEvent jogging = new JpaEvent("Morning Jogging", sofia, "Free Run", LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(24), Boolean.FALSE, Boolean.FALSE, EventCategory.SPORTS_EVENT, alex);
        final JpaEvent fishing = new JpaEvent("Morning Fishing", province, "Free Run",
                LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(24), Boolean.FALSE, Boolean.FALSE,
                EventCategory.SPORTS_EVENT, fani);

        this.eventRepository.save(jogging);
        this.eventRepository.save(fishing);
    }

    @Test
    public void createEventMustThrowExpectedIfAnUserWithDefinedUsernameDoesNotExists() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.createEvent(new BaseEventDTO("New Location", null, "No Description",
                    LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT),
                    "invalid");
        });
    }

    @Test
    public void editEventMustThrowExpectedIfEventDoesNotExists() {

        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.editEventById("invalid", new BaseEventDTO());
        });
    }

    @Test
    public void editEventMustPersistChangesIfEventExists() {
        final BaseEventDTO event = new BaseEventDTO("New Location", null, "No Description",
                LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT);
        final EventDTO eventCreated = this.eventService.createEvent(event, "alex123");
        event.setDescription("New Description");
        final EventDTO actual = this.eventService.editEventById(eventCreated.getId(), event);
        assertTrue(eventCreated.getId().equals(actual.getId()));
        assertTrue(actual.getDescription().equals(event.getDescription()));
    }

    @Test
    public void getEventByIdMustThrowExceptionWhenTheEventDoesNotExists() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.getEventById("invalid");
        });
    }

    @Test
    public void getEventByIdMustReturnCorrectIfTheEventExists() {
        final LocationDTO location = ObjectMapper.map(this.locationRepository.findByCity("Sofia").get(0),
                LocationDTO.class);
        final EventDTO event = this.eventService.createEvent(
                new BaseEventDTO("New Location", location, "No Description", LocalDateTime.now().plusHours(10),
                        LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT),
                "alex123");
        final EventDTO actual = this.eventService.getEventById(event.getId());
        assertEquals(event.getId(), actual.getId());
    }

    @Test
    public void banEventMustThrowExceptionWhenTheEventDoesNotExists() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.ban("invalid");
        });
    }

    @Test
    public void banEventMustChangeTheStatusOfBanned() {
        final LocationDTO location = ObjectMapper.map(this.locationRepository.findByCity("Sofia").get(0),
                LocationDTO.class);
        final EventDTO event = this.eventService.createEvent(
                new BaseEventDTO("New Location", location, "No Description", LocalDateTime.now().plusHours(10),
                        LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT),
                "alex123");
        this.eventService.ban(event.getId());
        final EventDTO actual = this.eventService.getEventById(event.getId());
        assertTrue(actual.getBanned());

    }

    @Test
    public void cancelEventMustThrowExceptionWhenTheEventDoesNotExists() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.cancelEventById("invalid");
        });
    }

    @Test
    public void cancelEventMustChangeTheStatusOfCancelledToTrue() {
        final LocationDTO location = ObjectMapper.map(this.locationRepository.findByCity("Sofia").get(0),
                LocationDTO.class);
        final EventDTO event = this.eventService.createEvent(
                new BaseEventDTO("New Location", location, "No Description", LocalDateTime.now().plusHours(10),
                        LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT),
                "alex123");
        this.eventService.cancelEventById(event.getId());
        final EventDTO actual = this.eventService.getEventById(event.getId());
        assertTrue(actual.getCancelled());

    }

    @Test
    public void getEventByOwnerMustThrowIfTheOwnerDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.getEventByOwner("invalid");
        });
    }
    @Test
    public void getEventByOwnerMustReturnEmptyIfThereIsNoEventForThatUser() {
        this.userRepository
        .save(new JpaUser("kosi123", "Aleksandar", "Stefanov", "aleksandar@gmail.com", Boolean.FALSE));
        assertTrue(this.eventService.getEventByOwner("kosi123").isEmpty());
    }

    @Test
    public void getEventByLocationMustReturnEmptyIfThereIsNoEventForThatLocation() {
        final JpaLocation location = this.locationRepository
                .save(new JpaLocation("New Location", "Sofia", LocationCategory.BAR));
        assertTrue(this.eventService.getEventByLocation(location.getId()).isEmpty());
    }

    @Test
    public void getEventByLocationMustThrowIfTheLocationDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.eventService.getEventByLocation("invalid");
        });
    }

    @Test
    public void getAllEventsMustReturnEmptyIfThereAreNotEvents() {
        this.eventRepository.deleteAll();
        assertThat(this.eventService.getAllEvents().isEmpty());
    }

    @Test
    public void getAllEventsMustReturnPresent() {
        assertThat(this.eventService.getAllEvents().size() == 2);
    }

    @Test
    public void getAllOuterEventsMustReturnEventsOfOtherPeople() {
        assertTrue(!this.eventService.getAllOuterEvents("alex123").get(0).getOwner().getUsername().equals("alex123"));
    }


    @AfterEach
    public void tearDown() {
        this.cleanUp();
    }

    private void cleanUp() {
        this.eventRepository.deleteAll();
        this.userRepository.deleteAll();
        this.locationRepository.deleteAll();
    }
}
