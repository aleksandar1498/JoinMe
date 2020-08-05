package com.enjoyit.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.enjoyit.domain.dto.NotificationDTO;

public interface NotificationService {
    /**
     * @param notification
     * @return
     */
    CompletableFuture<NotificationDTO> createNotification(NotificationDTO notification);
    /**
     * @param notificationId
     * @return
     */
    NotificationDTO markAsViewed(String notificationId);
    /**
     * @param userId
     * @return
     */
    List<NotificationDTO> getAllNotificationsForUser(String userId);
}
