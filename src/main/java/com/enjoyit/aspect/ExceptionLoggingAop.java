package com.enjoyit.aspect;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.enjoyit.enums.LoggerLevel;
import com.enjoyit.services.LoggingService;

@Aspect
@Configuration
public class ExceptionLoggingAop {

    @Autowired
    private LoggingService loggingService;

    @AfterThrowing(pointcut = "execution(* com.enjoyit.services.impl.*.*(..))", throwing = "e")
    public void logError(final JoinPoint point, final Throwable e) {
        final String className = point.getTarget().getClass().getSimpleName();
        final String method = MethodSignature.class.cast(point.getSignature()).getMethod().getName();
        final Object[] args = point.getArgs();
        final LocalDateTime when = LocalDateTime.now(ZoneOffset.UTC);
        final String errorMessage = (e.getMessage() == null) ? Strings.EMPTY
                : (e.getMessage().length() > 30) ? e.getMessage().substring(0, 30) : e.getMessage();
        System.out.println(e.getMessage());
        // DEBUG | 2008-09-06 10:51:45,458 | DefaultSingletonBeanRegistry.java | 213 |
        // Creating shared instance of singleton bean 'Oracle'
        this.loggingService.warn(String.format("%s | %s --- class : %s -> method : %s args (%s) - %s ",
                LoggerLevel.WARN, when.toString(), className, method, args, errorMessage));
    }
}
