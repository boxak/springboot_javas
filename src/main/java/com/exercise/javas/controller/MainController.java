package com.exercise.javas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
    @GetMapping({"/","/main"})
    public ModelAndView first() {
        return new ModelAndView("main");
    }

    @GetMapping("/about")
    public ModelAndView two() {
        return new ModelAndView("about");
    }

    @GetMapping("/developers")
    public ModelAndView three() {
        return new ModelAndView("team");
    }

    @GetMapping("/afterlogin")
    public ModelAndView afterlogin() {
        return new ModelAndView("redirect:main");
    }

    @GetMapping("/noticeResult")
    public ModelAndView noticeResult(@RequestParam(required = false,
            defaultValue = "normal") String msgType) {
        return new ModelAndView("noticeResult")
                .addObject("type",msgType);
    }
}
