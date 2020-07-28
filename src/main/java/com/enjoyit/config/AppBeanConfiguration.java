package com.enjoyit.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}
