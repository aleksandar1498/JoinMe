package com.enjoyit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.RoleDTO;
import com.enjoyit.domain.dto.UserEventDTO;
import com.enjoyit.domain.dto.UserWithEventsDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.enums.EventCategory;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.UserService;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

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

    @AfterEach
    public void tearDown() {
        this.cleanUp();
    }

    @Test
    public void findAllMustReturnAllThePresentUsersInThatMoment() {
        final List<UserWithRolesDTO> users = this.userService.findAllUsers();
        assertTrue(users.size() == 2);
    }

    @Test
    public void findAllMustReturnEmptyListIfThereAreNotPresentUsers() {
        this.cleanUp();
        final List<UserWithRolesDTO> users = this.userService.findAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    public void banUserMustChangeTheBannedStatusToTrueIfTheUserIsPresent() {
        this.userService.ban("alex123");
        final UserWithEventsDTO banned = this.userService.findByUsername("alex123");
        assertEquals("alex123", banned.getUsername());
        assertEquals(Boolean.TRUE, banned.getBanned());
    }

    @Test
    public void banUserMustThrowIfTheUserIsNotPresent() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.ban("invalid");
        });
    }

    @Test
    public void authorizeUserMustChangeTheBannedStatusToFalseIfTheUserIsPresent() {
        this.userService.authorize("alex123");
        final UserWithEventsDTO authorized = this.userService.findByUsername("alex123");
        assertEquals("alex123", authorized.getUsername());
        assertEquals(Boolean.FALSE, authorized.getBanned());
    }

    @Test
    public void authorizeUserMustThrowIfTheUserIsNotPresent() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.authorize("invalid");
        });
    }

    @Test
    public void updateRolesMustSubstituteTheCurrentIfTheUserExists() {
        this.userRepository
        .save(new JpaUser("alex999", "Aleksandar", "Stefanov", "aleksandar@gmail.com", Boolean.FALSE));

        final UserWithRolesDTO updated = new UserWithRolesDTO("U0001", "alex999", "Aleksandar", "Stefanov",
                "aleksandar@gmail.com", Boolean.FALSE);
        updated.setAuthorities(List.of(new RoleDTO(UserRoles.ORGANIZER), new RoleDTO(UserRoles.USER)));
        final UserWithRolesDTO actual = this.userService.updateRoles(updated);
        assertTrue(actual.getUsername().equals(updated.getUsername()));
    }

    @Test
    public void updateRolesMustThrowTheUserDoesNotExist() {

        final UserWithRolesDTO updated = new UserWithRolesDTO("U0001", "alex999", "Aleksandar", "Stefanov",
                "aleksandar@gmail.com", Boolean.FALSE);
        updated.setAuthorities(List.of(new RoleDTO(UserRoles.ORGANIZER), new RoleDTO(UserRoles.USER)));
        assertThrows(EntityNotFoundException.class,() -> {
            this.userService.updateRoles(updated);
        });
    }

    @Test
    public void joinEventShouldThrowExpectedIfTheEventDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.joinEvent("alex123", "invalid");
        });
    }

    @Test
    public void joinEventShouldThrowExpectedIfTheUserDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.joinEvent("invalid", "E0001");
        });
    }

    @Test
    public void joinEventMustReturnExpectedIfBothUserAndEventExists() {
        final String expectedEventId = this.eventRepository.findByOwnerUsername("fani123").get(0).getId();

        final UserEventDTO join = this.userService.joinEvent("alex123", expectedEventId);
        assertEquals("alex123", join.getUser().getUsername());
        assertEquals(expectedEventId, join.getEvent().getId());
    }

    @Test
    public void interestEventMustReturnExpectedIfBothUserAndEventExists() {
        final String expectedEventId = this.eventRepository.findByOwnerUsername("fani123").get(0).getId();

        final UserEventDTO interest = this.userService.interestEvent("alex123", expectedEventId);
        assertEquals("alex123", interest.getUser().getUsername());
        assertEquals(expectedEventId, interest.getEvent().getId());
    }

    @Test
    public void interestEventShouldThrowExpectedIfTheEventDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.interestEvent("alex123", "invalid");
        });
    }

    @Test
    public void interestEventShouldThrowExpectedIfTheUserDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.interestEvent("invalid", "E0001");
        });
    }

    @Test
    public void getInterestEventShouldThrowExpectedIfTheUserDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.getInterestedEvents("invalid");
        });
    }

    @Test
    public void disjoinEventShouldThrowExpectedIfTheEventDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.disjoinEvent("alex123", "invalid");
        });
    }

    @Test
    public void disjoinEventEventShouldThrowExpectedIfTheUserDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.disjoinEvent("invalid", "E0001");
        });
    }

    @Test
    public void disinterestEventEventShouldThrowExpectedIfTheUserDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.disinterestEvent("invalid", "E0001");
        });
    }

    @Test
    public void disinterestEventShouldThrowExpectedIfTheEventDoesNotExist() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.disinterestEvent("alex123", "invalid");
        });
    }

    @Test
    public void disjoinEventMustReturnExpectedIfBothUserAndEventExists() {
        final String expectedEventId = this.eventRepository.findByOwnerUsername("fani123").get(0).getId();
        this.userService.joinEvent("alex123", expectedEventId);
        this.userService.disjoinEvent("alex123", expectedEventId);
        final UserWithEventsDTO actual = this.userService.findByUsername("alex123");
        assertTrue(actual.getJoinedEvents().size() == 0);
    }

    @Test
    public void disinterestEventMustReturnExpectedIfBothUserAndEventExists() {
        final String expectedEventId = this.eventRepository.findByOwnerUsername("fani123").get(0).getId();
        this.userService.interestEvent("alex123", expectedEventId);
        this.userService.disinterestEvent("alex123", expectedEventId);
        final UserWithEventsDTO actual = this.userService.findByUsername("alex123");
        assertTrue(actual.getInterestedEvents().size() == 0);
    }

    @Test
    public void getInterestEventsMustThrowIfTheUserIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.getInterestedEvents("invalid");
        });
    }

    @Test
    public void getInterestEventsMustReturnEmptyIfThereAreNotInterestedEvents() {
        final List<EventDTO> events = this.userService.getInterestedEvents("alex123");
        assertTrue(events.isEmpty());
    }

    @Test
    public void getJoinedEventsMustThrowIfTheUserIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.getJoinedEvents("invalid");
        });
    }

    @Test
    public void getJoinedEventsMustReturnEmptyIfThereAreNotInterestedEvents() {
        final List<EventDTO> events = this.userService.getJoinedEvents("alex123");
        assertTrue(events.isEmpty());
    }

    private void cleanUp() {
        this.eventRepository.deleteAll();
        this.userRepository.deleteAll();
        this.locationRepository.deleteAll();
    }

}
