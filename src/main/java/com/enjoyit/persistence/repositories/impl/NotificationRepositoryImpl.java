package com.enjoyit.persistence.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.enjoyit.persistence.entities.JpaNotification;
import com.enjoyit.persistence.repositories.NotificationRepositoryCustom;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<JpaNotification> findAllByRecipientId(final String username) {
        System.out.println(username);
        return this.entityManager.createNamedQuery(JpaNotification.GET_ALL_BY_RECIPIENT).setParameter("username", username).getResultList();
    }

}
