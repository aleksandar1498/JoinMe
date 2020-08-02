package com.enjoyit.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.enjoyit.enums.LoggerLevel;

@Entity
@Table(name = "logs")
public class JpaLog {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
            )
    private String id;

    @Enumerated(EnumType.STRING)
    private LoggerLevel logLevel;

    @Column
    private String message;

    public JpaLog() {

    }

    public JpaLog(final LoggerLevel level, final String logMessage) {
        this.logLevel = level;
        this.message = logMessage;
    }

    public LoggerLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setLogLevel(final LoggerLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
