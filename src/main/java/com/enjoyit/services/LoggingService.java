package com.enjoyit.services;

import com.enjoyit.enums.LoggerLevel;

public interface LoggingService {
    void debug(String logMessage);

    void error(String logMessage);

    void fatal(String logMessage);

    void info(String logMessage);

    void log(LoggerLevel level, String logMessage);

    void trace(String logMessage);

    void warn(String logMessage);
}
