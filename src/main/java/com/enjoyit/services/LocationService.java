package com.enjoyit.services;

import java.util.List;
import java.util.Optional;

import com.enjoyit.domain.dto.LocationDTO;

/**
 * @author AStefanov
 */
public interface LocationService {
    /**
     * @param location
     * @return
     */
    LocationDTO create(LocationDTO location);

    /**
     * @param id
     */
    void delete(String id);

    /**
     * @return
     */
    List<LocationDTO> findAllLocations();

    /**
     * @param id
     * @return
     */
    Optional<LocationDTO> findLocationById(String id);

    /**
     * @param city
     * @return
     */
    List<LocationDTO> findLocationsByCity(String city);

    /**
     * @param id
     * @param location
     * @return
     */
    LocationDTO update(String id, LocationDTO location);
}
