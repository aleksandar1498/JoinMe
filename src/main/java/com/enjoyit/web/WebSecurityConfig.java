package com.enjoyit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    BCryptPasswordEncoder encoder;

    //@formatter:off
    /**
     * configuration for access different paths, and form login,logout
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/","/index","/login","/register","/events").permitAll()
                        .anyRequest().authenticated()
                    .and()
                .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler)
                    .and()
                .logout()
                        .permitAll();

     }
    //@formatter:on





}
