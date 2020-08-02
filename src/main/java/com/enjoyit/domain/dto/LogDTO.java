package com.enjoyit.domain.dto;

import com.enjoyit.enums.LoggerLevel;

public class LogDTO {
    private LoggerLevel logLevel;
    private String message;

    public LogDTO() {
    }

    public LoggerLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(final LoggerLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }


}
