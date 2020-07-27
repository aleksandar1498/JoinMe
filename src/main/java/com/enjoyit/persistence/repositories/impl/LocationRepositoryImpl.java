package com.enjoyit.persistence.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.entities.JpaLocation;
import com.enjoyit.persistence.repositories.LocationRepositoryCustom;

@Repository
@Transactional
public class LocationRepositoryImpl implements LocationRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(final String id) {
        final JpaLocation location = this.entityManager.find(JpaLocation.class, id);
        if (location == null) {
            throw new EntityNotFoundException("Location with this id does not exists");
        }

        location.getEvents().forEach(e -> {
            e.setLocation(null);
            e.setCancelled();
            this.entityManager.persist(e);
        });
        this.entityManager.remove(location);
    }

}
