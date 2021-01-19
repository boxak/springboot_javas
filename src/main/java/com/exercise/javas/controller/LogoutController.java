package com.exercise.javas.controller;

import com.exercise.javas.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class LogoutController {

    @Autowired
    LogoutService logoutService;

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        return logoutService.logout(session);
    }
}
