package com.enjoyit.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ExceptionLoggingAop {

    @AfterThrowing(pointcut = "execution(* com.enjoyit.services.impl.*.*(..))", throwing = "e")
    public void logError(final JoinPoint point, final Throwable e) {
        System.err.println("Caugth " + point + " error " + e.getMessage());
    }
}
