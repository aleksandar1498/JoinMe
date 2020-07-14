package com.enjoyit.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enjoyit.services.EventService;
import com.enjoyit.services.LoggingService;

@Component
public class SchedulerConfig {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private EventService eventService;
    @Autowired
    private LoggingService logService;

    @Scheduled(cron = "0 0 0 * * *")
    public void cancelExpiredEvents() {
        final int affectedRows = this.eventService.cleanUpExpiredEvents();
        logService.info(String.format("Clean up of expired events performed at %s , entities affected %d",
                LocalDateTime.now(), affectedRows));
    }
}
