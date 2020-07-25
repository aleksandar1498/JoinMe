package com.enjoyit.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjoyit.config.JwtTokenUtil;
import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.Role;
import com.enjoyit.persistence.repositories.RoleRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil tokenUtil;

    @Autowired
    public AuthServiceImpl(final UserRepository repository, final BCryptPasswordEncoder encoder,
            final AuthenticationManager authenticationManager, final JwtTokenUtil tokenUtil,
            final RoleRepository roleRepo) {
        this.userRepo = repository;
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
        this.roleRepo = roleRepo;
    }

    private Set<Role> getRolesForRegistration(final UserRegisterDTO user) {
        final Set<Role> roles = new HashSet<>();
        if (this.userRepo.count() == 0) {
            roles.add(this.roleRepo.findByAuthority(UserRoles.ADMIN));
        } else {
            if (Boolean.TRUE.equals(user.isOrganizer())) {
                roles.add(this.roleRepo.findByAuthority(UserRoles.ORGANIZER));
            } else {
                roles.add(this.roleRepo.findByAuthority(UserRoles.USER));
            }

        }

        return roles;
    }

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        final com.enjoyit.persistence.User user = this.userRepo.findByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException("A user with this username does not exists");
        });
        final Set<GrantedAuthority> auths = new HashSet<>(user.getAuthorities());
        return new User(user.getUsername(), user.getPassword(), auths);
    }

    @Override
    public LoggedInUserDTO login(final UserLoginDTO userModel) {
                final Authentication authentication = this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
                final SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                return new LoggedInUserDTO(authentication.getName(),"Bearer "+ this.tokenUtil.generateToken((User) authentication.getPrincipal()));
    }

    @Override
    public UserDTO register(final UserRegisterDTO user) {
        return null;
//        final ServiceResponse response = new ServiceResponse();
//        if (!user.getPassword().equals(user.getConfirmPassword())) {
//            response.setResponseMessage(MsgServiceResponse.NEW_PASSWORD_MISMATCHED);
//            response.setResponseCode(HttpStatus.BAD_REQUEST);
//            return response;
//        }
//        System.out.println("HERE " + user.getUsername());
//        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
//            response.setResponseMessage(MsgServiceResponse.USER_USERNAME_ALREADY_EXIST);
//            response.setResponseCode(HttpStatus.BAD_REQUEST);
//            return response;
//        }
//
//        final JpaUser userToPersist = ObjectMapper.map(user, JpaUser.class);
//        userToPersist.setAuthorities(getRolesForRegistration(user));
//        userToPersist.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
//        this.userRepo.save(userToPersist);
//        response.setSuccessResponse();
//        return response;
    }

}
