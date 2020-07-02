package com.enjoyit.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.entities.JpaLocation;

@Repository
public interface LocationRepository extends JpaRepository<JpaLocation, String>{
    /**
     * @param city
     * @return
     */
    List<JpaLocation> findByCity(String city);

}
