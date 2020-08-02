package com.enjoyit.services;

import com.enjoyit.domain.dto.LogDTO;
import com.enjoyit.enums.LoggerLevel;

public interface LoggingService {
    LogDTO debug(String logMessage);

    LogDTO error(String logMessage);

    LogDTO fatal(String logMessage);

    LogDTO info(String logMessage);

    LogDTO log(LoggerLevel level, String logMessage);

    LogDTO trace(String logMessage);

    LogDTO warn(String logMessage);
}
