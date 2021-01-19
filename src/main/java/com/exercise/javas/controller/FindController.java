package com.exercise.javas.controller;

import com.exercise.javas.service.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FindController {
    @Autowired
    FindService findService;

    @GetMapping("/findID/Form")
    public ModelAndView findIdForm() {
        return new ModelAndView("findIdForm");
    }

    @GetMapping("/findPW/Form")
    public ModelAndView findPwForm() {
        return new ModelAndView("findPwForm");
    }

    @PostMapping("/findID")
    public ModelAndView findID(String name, String phone) {
        return findService.findID(name, phone);
    }

    @PostMapping("/findPW")
    public ModelAndView findPW(String id, String name, String email) {
        return findService.findPW(id, name, email);
    }

    @GetMapping("/resetPwForm")
    public ModelAndView resetPwForm(String email) {
        return findService.resetPwForm(email);
    }

    @ResponseBody
    @PostMapping("/resetPw")
    public String resetPw(String email, String password) {
        return findService.resetPw(email, password);
    }
}
