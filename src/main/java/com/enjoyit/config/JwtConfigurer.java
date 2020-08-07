package com.enjoyit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.enjoyit.services.AuthService;
import com.enjoyit.utils.JwtTokenUtil;
import com.enjoyit.web.filters.JwtAuthorizationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenUtil tokenUtil;
    private final AuthService authService;

    @Autowired
    public JwtConfigurer(final JwtTokenUtil tokenUtil, final AuthService authService) {
        this.tokenUtil = tokenUtil;
        this.authService = authService;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final JwtAuthorizationFilter customFilter = new JwtAuthorizationFilter(tokenUtil,authService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
