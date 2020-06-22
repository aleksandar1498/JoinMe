package com.enjoyit.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enjoyit.domain.entities.JpaUser;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, String> {
    /**
     * @param username
     * @return
     */
    @Query(value="select * from users u where binary u.username = :username",nativeQuery = true)
    Optional<JpaUser> findByUsername(@Param("username") String username);


}
