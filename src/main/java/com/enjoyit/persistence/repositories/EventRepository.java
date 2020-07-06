package com.enjoyit.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.entities.JpaEvent;

@Repository
public interface EventRepository extends JpaRepository<JpaEvent, String>, EventRepositoryCustom {
    /**
     * @param username
     * @return
     */
    @Override
    Optional<JpaEvent> findById(String id);

    /**
     * @param location
     *            is the desired location that needs to be find
     * @return a List of events in the specified location , otherwise returns an
     *         empty List
     */
    List<Event> findByLocation(String location);

    /**
     * @param username
     * @return
     */
    List<Event> findByOwnerUsername(String username);

}
