package com.enjoyit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ModelAndView showHome(final ModelAndView model) {
        model.setViewName("user/home");
        return model;
    }
}
