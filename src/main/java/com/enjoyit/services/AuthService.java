package com.enjoyit.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.enjoyit.domain.models.UserLoginModel;
import com.enjoyit.domain.models.UserRegisterModel;

/**
 * @author AStefanov
 *
 */
public interface AuthService extends UserDetailsService {

    /**
     * @param user
     * @return
     */
    ServiceResponse login(UserLoginModel user);


    /**
     * @param user
     */
    ServiceResponse register(UserRegisterModel user);
}
