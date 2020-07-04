package com.enjoyit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyit.enums.LoggerLevel;
import com.enjoyit.persistence.entities.JpaLog;
import com.enjoyit.services.LoggingService;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final LogRepository logRepository;

    @Autowired
    public LoggingServiceImpl(final LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void debug(final String logMessage) {
        this.log(LoggerLevel.DEBUG, logMessage);
    }

    @Override
    public void error(final String logMessage) {
        this.log(LoggerLevel.ERROR, logMessage);
    }

    @Override
    public void fatal(final String logMessage) {
        this.log(LoggerLevel.FATAL, logMessage);
    }

    @Override
    public void info(final String logMessage) {
        this.log(LoggerLevel.INFO, logMessage);
    }

    @Override
    public void log(final LoggerLevel level, final String logMessage) {
        this.logRepository.save(new JpaLog(level, logMessage));
    }

    @Override
    public void trace(final String logMessage) {
        this.log(LoggerLevel.TRACE, logMessage);
    }

    @Override
    public void warn(final String logMessage) {
        this.log(LoggerLevel.WARN, logMessage);
    }

}
