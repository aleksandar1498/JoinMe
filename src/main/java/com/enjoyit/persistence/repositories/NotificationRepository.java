package com.enjoyit.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoyit.persistence.entities.JpaNotification;


@Repository
public interface NotificationRepository extends JpaRepository<JpaNotification, String>,NotificationRepositoryCustom {


}
