package com.enjoyit.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.composite.UserInterestEventKey;
import com.enjoyit.persistence.entities.JpaUserInterestEvent;

@Repository
public interface UserInterestEventRepository extends JpaRepository<JpaUserInterestEvent, UserInterestEventKey> {

}
