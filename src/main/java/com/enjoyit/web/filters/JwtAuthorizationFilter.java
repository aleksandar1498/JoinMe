package com.enjoyit.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.enjoyit.services.AuthService;
import com.enjoyit.utils.JwtTokenUtil;


public class JwtAuthorizationFilter extends GenericFilterBean{

    private final JwtTokenUtil tokenUtil;
    private final AuthService authService;

    @Autowired
    public JwtAuthorizationFilter(final JwtTokenUtil tokenUtil,final AuthService auth) {
        this.tokenUtil = tokenUtil;
        this.authService = auth;
    }


    private UsernamePasswordAuthenticationToken getAuthentication(final String token) {
        final UserDetails userDetails = this.authService.loadUserByUsername(this.tokenUtil.getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)request;
        final HttpServletResponse resp = (HttpServletResponse)response;
        final String token = tokenUtil.resolveToken(req);
        if(token != null && tokenUtil.validateToken(token)) {
            final Authentication auth = this.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, resp);

    }
}
