package com.enjoyit.config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.enjoyit.enums.EventCategory;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.entities.JpaEvent;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaRole;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.EventRepository;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.persistence.repositories.RoleRepository;
import com.enjoyit.persistence.repositories.UserRepository;

@Component
public class DBInitialConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public void run(final String... args) throws Exception {
        if (this.roleRepository.count() == 0) {
            Arrays.stream(UserRoles.values()).forEach(r -> {
                this.roleRepository.saveAndFlush(new JpaRole(r));
            });
        }

        if (this.locationRepository.count() == 0) {
            this.locationRepository.saveAndFlush(new JpaLocation("SoftUni", "Sofia", LocationCategory.OTHER));
            this.locationRepository.saveAndFlush(new JpaLocation("Cult", "Varna", LocationCategory.NIGHT_CLUB));
            this.locationRepository.saveAndFlush(new JpaLocation("Winter Palace", "Sofia", LocationCategory.GYM));
            this.locationRepository.saveAndFlush(new JpaLocation("Russardi", "Targovishte", LocationCategory.BAR));
            this.locationRepository.saveAndFlush(new JpaLocation("Galeria Pool", "Obzor", LocationCategory.OTHER));

            this.locationRepository.saveAndFlush(new JpaLocation("Jungle Gym", "Plovdiv", LocationCategory.SPORT));
        }

        if (this.userRepository.count() == 0) {
            final JpaUser admin = new JpaUser("kalin77", "Kalin", "Kitanov", "kalin77@gmail.com");
            admin.setAuthorities(Set.of(this.roleRepository.findByAuthority(UserRoles.ADMIN)));
            admin.setPassword(encoder.encode("123"));
            this.userRepository.saveAndFlush(admin);
            final JpaUser user = new JpaUser("irina80", "Irina", "Kitanova", "irina80@gmail.com");
            user.setAuthorities(Set.of(this.roleRepository.findByAuthority(UserRoles.USER)));
            user.setPassword(encoder.encode("123"));

            this.userRepository.saveAndFlush(user);
            final JpaUser organizer = new JpaUser("alex98", "Aleksandar", "Stefanov", "alex98@gmail.com");
            organizer.setAuthorities(Set.of(this.roleRepository.findByAuthority(UserRoles.ORGANIZER)));
            organizer.setPassword(encoder.encode("123"));

            this.userRepository.saveAndFlush(organizer);

        }

        if (this.eventRepository.count() == 0) {
            final JpaUser user = this.userRepository.findByUsername("irina80").orElseThrow(() -> {
                return new EntityNotFoundException("User with this username is not found");
            });

            this.eventRepository.saveAndFlush(new JpaEvent("Code Talk", this.locationRepository.findByCity("Sofia").get(0),
                    "Talking about programming", LocalDateTime.now().minusMonths(2).minusHours(2),
                    LocalDateTime.now().minusMonths(1), Boolean.FALSE, Boolean.FALSE, EventCategory.CONFERENCE_EVENT,
                    user));
            this.eventRepository.saveAndFlush(new JpaEvent("Pool Party", this.locationRepository.findByCity("Varna").get(0),
                    "Fresh swim in the deep night", LocalDateTime.now().minusDays(10).minusHours(2),
                    LocalDateTime.now().minusDays(10).plusHours(2), Boolean.FALSE, Boolean.FALSE,
                    EventCategory.MUSIC_EVENT, user));

            final JpaUser organizer =this.userRepository.findByUsername("alex98").orElseThrow(() -> {
                return new EntityNotFoundException("User with this username is not found");
            });

            this.eventRepository.saveAndFlush(new JpaEvent("Cross Fit", this.locationRepository.findByCity("Plovdiv").get(0),
                    "Advanced cross-fit workout", LocalDateTime.now().minusMonths(6).minusHours(2),
                    LocalDateTime.now().minusMonths(6), Boolean.FALSE, Boolean.FALSE, EventCategory.SPORTS_EVENT,
                    organizer));

            this.eventRepository.saveAndFlush(new JpaEvent("Yoga", this.locationRepository.findByCity("Plovdiv").get(0),
                    "Yoga session for relax", LocalDateTime.now().minusMonths(4).minusHours(2),
                    LocalDateTime.now().minusMonths(4), Boolean.FALSE, Boolean.FALSE, EventCategory.SPORTS_EVENT,
                    organizer));

        }
    }

}
