package com.enjoyit.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.entities.JpaLog;

@Repository
public interface LogRepository extends JpaRepository<JpaLog, String> {

}
