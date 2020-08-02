package com.enjoyit.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<LocationDTO> create(@Validated @RequestBody final LocationDTO location,final UriComponentsBuilder ucBuilder){
        final LocationDTO created = this.locationService.create(location);
        return ResponseEntity.created(ucBuilder.path("/locations/{id)").buildAndExpand(created.getId()).toUri()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<LocationDTO> delete(@PathVariable("id") final String id){
        this.locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> findAllLocations(){
        return ResponseEntity.ok(this.locationService.findAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> findLocationById(@PathVariable("id") final String id){
        return this.locationService.findLocationById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<LocationDTO> update(@Validated @RequestBody final LocationDTO location,@PathVariable("id") final String id,final UriComponentsBuilder ucBuilder){
        System.out.println(location+" here "+id);
        final LocationDTO updated = this.locationService.update(id,location);
        System.out.println(updated);
        return ResponseEntity.created(ucBuilder.path("/locations/{id}").buildAndExpand(updated .getId()).toUri()).build();
    }

}
