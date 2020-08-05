package com.enjoyit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.enjoyit.utils.JwtTokenUtil;
import com.enjoyit.web.filters.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public JwtTokenUtil tokenUtil;
    /**
     * BEAN needed for the authentication
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //@formatter:off
    /**
     * configuration for access different paths, and form login,logout
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/","/user/register" ,"/user/login","/user").permitAll()
        .antMatchers("/admin","/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .cors().configurationSource(simpleCorsFilter())
        .and()
        .addFilter(new JwtAuthorizationFilter(authenticationManagerBean(),tokenUtil))
        .csrf()
        .disable();



    }
    //@formatter:on
    /**
     *
     * @return a valid cors configuration
     */
    @Bean
    public CorsConfigurationSource simpleCorsFilter(){
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://ec2-18-216-125-212.us-east-2.compute.amazonaws.com"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
