package com.enjoyit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.enjoyit.enums.LocationCategory;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.entities.JpaRole;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.persistence.repositories.RoleRepository;

@Component
public class DBInitialConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LocationRepository locationRepository;
    @Override
    public void run(final String... args) throws Exception {
        if (this.roleRepository.count() == 0) {
            Arrays.stream(UserRoles.values()).forEach(r -> {
                this.roleRepository.save(new JpaRole(r));
            });
        }

        if(this.locationRepository.count() == 0) {
            this.locationRepository.save(new JpaLocation("Petar Uvaliev 1", "Sofia", LocationCategory.CAFE));
            this.locationRepository.save(new JpaLocation("Osen 1", "Strazha", LocationCategory.MOVIE_THEATER));
            this.locationRepository.save(new JpaLocation("Aleko Konstantinov 3", "Targovishte", LocationCategory.RESTAURANT));
            this.locationRepository.save(new JpaLocation("Pirin Planina 8", "Targovishte", LocationCategory.OTHER));
            this.locationRepository.save(new JpaLocation("Zapad 2 Blok 40", "Targovishte", LocationCategory.NIGHT_CLUB));
        }

    }

}
