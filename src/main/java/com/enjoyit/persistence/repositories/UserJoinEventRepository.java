package com.enjoyit.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.composite.UserJoinEventKey;
import com.enjoyit.persistence.entities.JpaUserJoinEvent;

@Repository
public interface UserJoinEventRepository extends JpaRepository<JpaUserJoinEvent, UserJoinEventKey> {

}
