package com.enjoyit.web.controllers;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.services.AuthService;

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
    public ResponseEntity<LoggedInUserDTO> login(@RequestBody final UserLoginDTO user) {
        return new ResponseEntity<LoggedInUserDTO>(this.userService.login(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<UserDTO> registerUser(@Validated @RequestBody final UserRegisterDTO user,
            final UriComponentsBuilder ucBuilder) {
        final UserDTO registered = this.userService.register(user);
        return ResponseEntity.created(ucBuilder.path("/users/{id)").buildAndExpand(registered.getId()).toUri()).build();
    }

    @GetMapping("/user")
    public Principal user(final HttpServletRequest request) {
        final String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

}
