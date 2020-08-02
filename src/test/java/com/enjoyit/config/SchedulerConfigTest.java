package com.enjoyit.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enjoyit.enums.EventCategory;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.persistence.repositories.UserRepository;

@SpringBootTest
class SchedulerConfigTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SchedulerConfig scheduler;

    @BeforeEach
    public void setUp() {
        this.eventRepository.deleteAll();
        this.userRepository.deleteAll();
        this.locationRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        this.eventRepository.deleteAll();
        this.userRepository.deleteAll();
        this.locationRepository.deleteAll();
    }

    @Test
    public void testIfEventsFromThePastAreCancelled() {
        final JpaUser fani = this.userRepository
                .save(new JpaUser("fani123", "Fani", "Ah", "fani@gmail.com", Boolean.FALSE));
        final JpaLocation sofia = this.locationRepository

                .save(new JpaLocation("Ploshtad Aleko", "Sofia", LocationCategory.BAR));

        final JpaEvent event = this.eventRepository.save(new JpaEvent("Morning Jogging", sofia, "Free Run", LocalDateTime.now().minusHours(10),
                LocalDateTime.now().minusHours(2), Boolean.FALSE, Boolean.FALSE, EventCategory.SPORTS_EVENT, fani));

        this.scheduler.cancelExpiredEvents();

        assertTrue(this.eventRepository.findById(event.getId()).get().getCancelled().equals(Boolean.TRUE));
    }

    @Test
    public void testIfTheEventIsNotExpireItIsNotCancelled() {
        final JpaUser fani = this.userRepository
                .save(new JpaUser("fani123", "Fani", "Ah", "fani@gmail.com", Boolean.FALSE));
        final JpaLocation sofia = this.locationRepository

                .save(new JpaLocation("Ploshtad Aleko", "Sofia", LocationCategory.BAR));

        final JpaEvent event = this.eventRepository.save(new JpaEvent("Morning Jogging", sofia, "Free Run", LocalDateTime.now().plusHours(10),
                LocalDateTime.now().plusHours(24), Boolean.FALSE, Boolean.FALSE, EventCategory.SPORTS_EVENT, fani));

        this.scheduler.cancelExpiredEvents();

        assertTrue(this.eventRepository.findById(event.getId()).get().getCancelled().equals(Boolean.FALSE));
    }
}
