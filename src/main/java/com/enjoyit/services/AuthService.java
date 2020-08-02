package com.enjoyit.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;

/**
 * @author AStefanov
 *
 */
public interface AuthService extends UserDetailsService {

    /**
     * @param user
     * @return
     */
    LoggedInUserDTO login(UserLoginDTO user);


    /**
     * @param user
     */
    UserWithRolesDTO register(UserRegisterDTO user);


}
