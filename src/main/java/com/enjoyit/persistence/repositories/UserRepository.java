package com.enjoyit.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.entities.JpaUser;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, String> {
    /**
     * @param username
     * @return
     */
    Optional<JpaUser> findByUsername(String username);
}
