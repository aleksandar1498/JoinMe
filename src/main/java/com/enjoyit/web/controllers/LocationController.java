package com.enjoyit.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.services.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }


    @PostMapping
    public ResponseEntity create(@RequestBody final LocationDTO location){
        return new ResponseEntity<LocationDTO>(this.locationService.create(location), HttpStatus.CREATED);
    }

    @GetMapping
    public List<LocationDTO> findAllLocations(){
        final List<LocationDTO> locations = this.locationService.findAllLocations();

        return locations;
    }

    @GetMapping("/{id}")
    public Optional<LocationDTO> findLocationById(@PathVariable("id") final String id){
        return this.locationService.findLocationById(id);
    }

}
