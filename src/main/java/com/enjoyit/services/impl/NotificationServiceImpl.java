package com.enjoyit.services.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.NotificationDTO;
import com.enjoyit.persistence.User;
import com.enjoyit.persistence.entities.JpaNotification;
import com.enjoyit.persistence.repositories.NotificationRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.NotificationService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    @Autowired
    public NotificationServiceImpl(final NotificationRepository notificationRepository,final UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Async
    @Override
    public CompletableFuture<NotificationDTO> createNotification(final NotificationDTO notification) {
        final User user = this.userRepository.getOne(notification.getRecipient().getId());
        final JpaNotification notificationToPersist = ObjectMapper.map(notification,JpaNotification.class);
        notificationToPersist.setRecipient(user);
        return CompletableFuture.completedFuture(ObjectMapper.map(this.notificationRepository.save(notificationToPersist),NotificationDTO.class));
    }

    @Override
    public NotificationDTO markAsViewed(final String notificationId) {
        final JpaNotification notification = this.notificationRepository.findById(notificationId).orElseThrow(() -> {
            return new EntityNotFoundException("A notification with this id does not exists");
        });

        notification.setIsViewed(Boolean.TRUE);

        return ObjectMapper.map(this.notificationRepository.save(notification),NotificationDTO.class);
    }

    @Override
    public List<NotificationDTO> getAllNotificationsForUser(final String userId) {
        System.out.println(this.notificationRepository.findAllByRecipientId(userId).size());
        return ObjectMapper.mapAll(this.notificationRepository.findAllByRecipientId(userId), NotificationDTO.class);
    }

}
