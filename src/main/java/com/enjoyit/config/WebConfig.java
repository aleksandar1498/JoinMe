package com.enjoyit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.enjoyit.utils.StringToRoleDTOConverterFactory;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverterFactory(new StringToRoleDTOConverterFactory());
    }

}
