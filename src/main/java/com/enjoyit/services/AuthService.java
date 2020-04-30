package com.enjoyit.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.enjoyit.domain.models.UserLoginModel;

public interface AuthService extends UserDetailsService {
    /**
     * @param user
     */
    void register(UserLoginModel user);
}
