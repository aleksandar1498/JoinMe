package com.enjoyit.web.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.enjoyit.config.JwtTokenUtil;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil tokenUtil;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager, final JwtTokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        Authentication auth = null;
        System.out.println("here");
        try {
            final UserLoginDTO creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginDTO.class);
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (final IOException e) {
            // failed auth
            response.setStatus(418);
        }
        return auth;
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res,
            final FilterChain chain, final Authentication auth) throws IOException {
        final String token = tokenUtil.generateToken(((User) auth.getPrincipal()));

        res.addHeader("Authorization", "Bearer " + token);
    }

}
