package com.enjoyit.config;

import java.util.concurrent.Executor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.enjoyit.aspect.ExceptionLoggingAop;

@Configuration
public class AppBeanConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ExceptionLoggingAop getLoggingAop() {
        return new ExceptionLoggingAop();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "asyncExecutor")
    public Executor taskExecutor() {
        final SimpleAsyncTaskExecutor  executor =  new SimpleAsyncTaskExecutor();
        executor.setThreadNamePrefix("Notification -");
        executor.setConcurrencyLimit(-1);
        //final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /* executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Notification -");
        executor.initialize();*/
        return executor;
    }

}
