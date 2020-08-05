package com.enjoyit.persistence.repositories;

import java.util.List;

import com.enjoyit.persistence.entities.JpaNotification;

public interface NotificationRepositoryCustom {
    List<JpaNotification> findAllByRecipientId(String id);
}
