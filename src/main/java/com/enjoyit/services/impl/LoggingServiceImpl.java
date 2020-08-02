package com.enjoyit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.dto.LogDTO;
import com.enjoyit.enums.LoggerLevel;
import com.enjoyit.persistence.entities.JpaLog;
import com.enjoyit.persistence.repositories.LogRepository;
import com.enjoyit.services.LoggingService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class LoggingServiceImpl implements LoggingService {
    private final LogRepository logRepository;
    @Autowired
    public LoggingServiceImpl(final LogRepository logRepository) {
        this.logRepository = logRepository;

    }

    @Override
    public LogDTO debug(final String logMessage) {
        return this.log(LoggerLevel.DEBUG, logMessage);
    }

    @Override
    public LogDTO error(final String logMessage) {
        return this.log(LoggerLevel.ERROR, logMessage);

    }

    @Override
    public LogDTO fatal(final String logMessage) {
        return this.log(LoggerLevel.FATAL, logMessage);

    }

    @Override
    public LogDTO info(final String logMessage) {
        return this.log(LoggerLevel.INFO, logMessage);

    }

    @Override
    public LogDTO log(final LoggerLevel level, final String logMessage) {
        return ObjectMapper.map(this.logRepository.save(new JpaLog(level, logMessage)),LogDTO.class);

    }

    @Override
    public LogDTO trace(final String logMessage) {
        return this.log(LoggerLevel.TRACE, logMessage);

    }

    @Override
    public LogDTO warn(final String logMessage) {
        return this.log(LoggerLevel.WARN, logMessage);

    }

}
