package com.enjoyit.services.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enjoyit.enums.LoggerLevel;
import com.enjoyit.services.LoggingService;

@SpringBootTest
class LoggingServiceImplTest {
    @Autowired
    private LoggingService logService;


    @Test
    public void log() {
        assertTrue(this.logService.log(LoggerLevel.DEBUG, "New messaged").getLogLevel().equals(LoggerLevel.DEBUG));
    }

    @Test
    public void logNull() {
        assertTrue(this.logService.log(LoggerLevel.DEBUG, null).getLogLevel().equals(LoggerLevel.DEBUG));
    }

    @Test
    public void info() {
        assertTrue(this.logService.info("New messaged").getLogLevel().equals(LoggerLevel.INFO));
    }

    @Test
    public void debug() {
        assertTrue(this.logService.debug("New messaged").getLogLevel().equals(LoggerLevel.DEBUG));
    }

    @Test
    public void warn() {
        assertTrue(this.logService.warn("New messaged").getLogLevel().equals(LoggerLevel.WARN));
    }

    @Test
    public void fatal() {
        assertTrue(this.logService.fatal("New messaged").getLogLevel().equals(LoggerLevel.FATAL));
    }

    @Test
    public void error() {
        assertTrue(this.logService.error("New messaged").getLogLevel().equals(LoggerLevel.ERROR));
    }

    @Test
    public void trace() {
        assertTrue(this.logService.trace("New messaged").getLogLevel().equals(LoggerLevel.TRACE));
    }

}
