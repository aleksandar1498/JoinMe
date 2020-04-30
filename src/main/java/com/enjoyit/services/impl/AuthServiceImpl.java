package com.enjoyit.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjoyit.domain.entities.JpaRole;
import com.enjoyit.domain.entities.JpaUser;
import com.enjoyit.domain.models.UserLoginModel;
import com.enjoyit.persistence.UserRepository;
import com.enjoyit.services.AuthService;
import com.enjoyit.utils.ObjectMapper;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(final UserRepository repository, final BCryptPasswordEncoder encoder) {
        this.userRepo = repository;
        this.bCryptPasswordEncoder = encoder;
    }

    private Set<JpaRole> getRolesForRegistration() {
        final Set<JpaRole> roles = new HashSet<JpaRole>();

        if (this.userRepo.count() == 0) {
            roles.add(new JpaRole("ADMIN"));
        } else {
            roles.add(new JpaRole("USER"));
        }

        return roles;
    }

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        final com.enjoyit.persistence.User user = this.userRepo.findJpaUserByUsername(username).orElseThrow(() -> {
            throw new IllegalArgumentException("A user with this username does not exists");
        });
        final Set<GrantedAuthority> auths = new HashSet<>(user.getAuthorities());
        return new User(user.getUsername(), user.getPassword(), auths);
    }

    @Override
    public void register(final UserLoginModel user) {
        final JpaUser userToPersist = ObjectMapper.map(user,JpaUser.class);
        userToPersist.setAuthorities(getRolesForRegistration());
        userToPersist.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepo.save(userToPersist);
    }

}
