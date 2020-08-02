package com.enjoyit.services.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

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
import org.springframework.validation.annotation.Validated;

import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.enums.MsgServiceResponse;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.Role;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.RoleRepository;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.AuthService;
import com.enjoyit.utils.JwtTokenUtil;
import com.enjoyit.utils.ObjectMapper;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil tokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(final UserRepository repository, final BCryptPasswordEncoder encoder,
            final AuthenticationManager authenticationManager, final JwtTokenUtil tokenUtil,
            final RoleRepository roleRepo) {
        this.userRepo = repository;
        this.bCryptPasswordEncoder = encoder;
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
        if(Boolean.TRUE.equals(user.getBanned())) {
            throw new IllegalArgumentException("You are not authorized to access,because you are banned");
        }
        final Set<GrantedAuthority> auths = new HashSet<>(user.getAuthorities());
        return new User(user.getUsername(), user.getPassword(), auths);
    }

    @Override
    public LoggedInUserDTO login(@Validated final UserLoginDTO userModel) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return new LoggedInUserDTO(authentication.getName(),
                "Bearer " + this.tokenUtil.generateToken((User) authentication.getPrincipal()));
    }

    @Override
    public UserWithRolesDTO register(@Validated final UserRegisterDTO user) {

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException(MsgServiceResponse.USER_USERNAME_ALREADY_EXIST.toString());
        }
        final JpaUser userToPersist = ObjectMapper.map(user, JpaUser.class);
        userToPersist.setAuthorities(getRolesForRegistration(user));
        userToPersist.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        return ObjectMapper.map(this.userRepo.save(userToPersist), UserWithRolesDTO.class);
    }

}
