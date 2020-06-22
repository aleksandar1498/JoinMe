package com.enjoyit.web.controllers;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyit.domain.models.UserLoginModel;
import com.enjoyit.domain.models.UserRegisterModel;
import com.enjoyit.services.AuthService;
import com.enjoyit.services.ServiceResponse;

@RestController
@RequestMapping("/user")
public class AuthController {

    private final AuthService userService;

    @Autowired
    public AuthController(final AuthService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    @ResponseBody
    public ServiceResponse login(@RequestBody final UserLoginModel user) {
        return this.userService.login(user);
    }

    @PostMapping("/register")
    @ResponseBody
    public ServiceResponse registerUser(@RequestBody final UserRegisterModel user) {
        System.out.println(user.getUsername()+" "+user.getConfirmPassword()+" "+user.isOrganizer());
        return this.userService.register(user);
    }

    @GetMapping("/user")
    public Principal user(final HttpServletRequest request) {
        final String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

}
