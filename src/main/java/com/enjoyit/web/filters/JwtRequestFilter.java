package com.enjoyit.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

@WebFilter
public class JwtRequestFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader == null) {
            throw new IllegalAccessError("You are not allowed to access");
        }
    }

}
