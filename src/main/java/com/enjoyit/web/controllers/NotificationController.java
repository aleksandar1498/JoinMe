package com.enjoyit.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.dto.NotificationDTO;
import com.enjoyit.services.NotificationService;

@RestController
@RequestMapping(path = {"/notifications"})
public class NotificationController {

    private final NotificationService notificationService;


    @Autowired
    public NotificationController(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsForUser(@PathVariable("id") final String id){
        return ResponseEntity.ok(this.notificationService.getAllNotificationsForUser(id));
    }

    @PutMapping("/view/{id}")
    public ResponseEntity<NotificationDTO> makeNotificationAsViewed(@PathVariable("id") final String notificationId){
        return ResponseEntity.ok(this.notificationService.markAsViewed(notificationId));
    }

}
