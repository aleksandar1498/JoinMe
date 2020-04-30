package com.enjoyit.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyit.domain.models.UserLoginModel;
import com.enjoyit.services.AuthService;

@Controller
public class AuthController {

    private final AuthService userService;
    private final ModelMapper mapper;

    @Autowired
    public AuthController(final AuthService userService,final ModelMapper mapper) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute final UserLoginModel user,final ModelAndView view) {
       this.userService.register(user);
       view.setViewName("redirect:/login");
       return view;
    }





}
