package com.exercise.javas.controller;

import com.exercise.javas.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ModelAndView login(String id, String password, String isSaveId,
                              HttpSession session, HttpServletResponse response) {
        return loginService.login(id,password,isSaveId,session,response);
    }

    @GetMapping("/login/form")
    public ModelAndView loginform() {
        return new ModelAndView("loginform");
    }

}
