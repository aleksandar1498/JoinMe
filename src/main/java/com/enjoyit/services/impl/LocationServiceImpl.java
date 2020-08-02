package com.enjoyit.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.repositories.LocationRepository;
import com.enjoyit.services.LocationService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepo;

    @Autowired
    public LocationServiceImpl(final LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    public LocationDTO create(@Validated final LocationDTO location) {
        final JpaLocation locationToSave = ObjectMapper.map(location, JpaLocation.class);
        return ObjectMapper.map(this.locationRepo.save(locationToSave), LocationDTO.class);
    }

    @Override
    public void delete(final String id) {
        this.locationRepo.delete(id);
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        return ObjectMapper.mapAll(this.locationRepo.findAll(), LocationDTO.class);
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

    @Override
    public LocationDTO update(final String id,final LocationDTO updated) {
        final JpaLocation locationToUpdate = this.locationRepo.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Location with this id does not exists");
        });
        locationToUpdate.setAddress(updated.getAddress());
        locationToUpdate.setCity(updated.getCity());

        return ObjectMapper.map(this.locationRepo.save(locationToUpdate), LocationDTO.class);
    }

}
