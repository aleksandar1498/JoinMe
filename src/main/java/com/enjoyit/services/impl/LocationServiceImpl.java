package com.enjoyit.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.services.LocationService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepo;

    public LocationServiceImpl(final LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    public LocationDTO create(final LocationDTO location) {
        final JpaLocation locationToSave = ObjectMapper.map(location, JpaLocation.class);
        return ObjectMapper.map(this.locationRepo.save(locationToSave), LocationDTO.class);
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        final List<JpaLocation> locations = this.locationRepo.findAll();
        locations.forEach(l -> {
            System.out.println(l.toString());
        });
        return ObjectMapper.mapAll(locations, LocationDTO.class);
    }

    @Override
    public Optional<LocationDTO> findLocationById(final String id) {
        return this.locationRepo.findById(id).map(l -> {
            return Optional.of(ObjectMapper.map(l, LocationDTO.class));
        }).orElse(Optional.empty());

    }

    @Override
    public List<LocationDTO> findLocationsByCity(final String city) {
        return ObjectMapper.mapAll(this.locationRepo.findByCity(city), LocationDTO.class);
    }

}
