package com.enjoyit.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.TransactionSystemException;

import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.services.LocationService;

@SpringBootTest
class LocationServiceImplTest {
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void setUp() {
        this.locationRepository.deleteAll();
        this.locationRepository.save(new JpaLocation("The Point", "Sofia", LocationCategory.BAR));
        this.locationRepository.save(new JpaLocation("Russardi", "Targovishte", LocationCategory.NIGHT_CLUB));
        this.locationRepository.save(new JpaLocation("Cuba", "Sofia", LocationCategory.OTHER));
    }

    @Test
    public void findLocationByCityMustReturnEmptyIfLocationWithThisCityDoesNotExist() {
        assertTrue(this.locationService.findLocationsByCity("Tarnovo").isEmpty());
    }

    @Test
    public void findLocationByCityMustReturnAllLocationsWithThatCityName() {
        assertTrue(this.locationService.findLocationsByCity("Sofia").size() == 2);
    }

    @Test
    public void findAllLocationsMustReturnEmptyIfThereAreNotRegistered() {
        this.locationRepository.deleteAll();
        assertTrue(this.locationService.findAllLocations().isEmpty());
    }

    @Test
    public void findAllLocationsMustReturnAllPresentLocations() {
        assertTrue(this.locationService.findAllLocations().size() == 3);
    }

    @Test
    public void createLocationMustThrowValidatioExceptionIfLocationIsNotValid() {
        assertThrows(TransactionSystemException.class, () -> {
            this.locationService.create(new LocationDTO());
        });
    }

    @Test
    public void createLocationMustPersistNewLocation() {
        final LocationDTO location = this.locationService
                .create(new LocationDTO("Balchik", "No", LocationCategory.MUSEUM));
        assertThat(location.getCity().equals("Balchik"));
        assertThat(location.getAddress().equals("No"));
        assertThat(location.getLocationCategory().equals(LocationCategory.MUSEUM));
    }

    @Test
    public void updateLocationMustThrowIfLocationIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.locationService.update("invalid", new LocationDTO());
        });
    }

    @Test
    public void updateLocationMustChangeTheValuesOfTheAlreadyPersistedLocation() {

        final LocationDTO location = this.locationService
                .create(new LocationDTO("Balchik", "No", LocationCategory.MUSEUM));
        location.setAddress("New Address");
        final LocationDTO updated = this.locationService.update(location.getId(), location);

        assertThat(updated.getAddress().equals("New Address"));
    }

    @Test
    public void findLocationByIdMustReturnEmptyOptionalIfItIsNotPresent() {
        assertTrue(this.locationService.findLocationById("invalid").isEmpty());
    }

    @Test
    public void findLocationByIdMustReturnCorrectIfPresent() {
        final LocationDTO location = this.locationService
                .create(new LocationDTO("Balchik", "No", LocationCategory.MUSEUM));
        assertTrue(this.locationService.findLocationById(location.getId()).isPresent());
    }
    @Test
    public void deleteLocationMustThrowIfLocationDoesNotExists() {
        assertThrows(ObjectRetrievalFailureException.class, () -> {
            this.locationService.delete("invalid");
        });
    }

    @Test
    public void deleteLocationMustDeleteTheLocationById() {
        final LocationDTO location = this.locationService
                .create(new LocationDTO("Balchik", "No", LocationCategory.MUSEUM));

        this.locationService.delete(location.getId());
        assertTrue(this.locationRepository.findById(location.getId()).isEmpty());

    }

}
