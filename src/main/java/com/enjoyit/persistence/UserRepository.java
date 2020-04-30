package com.enjoyit.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.domain.entities.JpaUser;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, String> {
    /**
     * @param username
     * @return
     */
    Optional<User> findJpaUserByUsername(String username);
}
