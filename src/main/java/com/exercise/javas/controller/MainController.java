package com.exercise.javas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class MainController {
    @GetMapping({"/","/main"})
    public ModelAndView first() {
        log.info("들어왔어요!");
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
            defaultValue = "normal") String type) {
        return new ModelAndView("noticeResult")
                .addObject("type",type);
    }
}
