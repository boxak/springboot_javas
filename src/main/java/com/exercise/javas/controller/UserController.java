package com.exercise.javas.controller;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.service.UserService;
import com.mongodb.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/signForm")
    public String signForm() {
        return "signForm";
    }

    @PostMapping(value = "/signin.do")
    public ModelAndView insert(@Valid UserDTO dto, @Nullable MultipartFile photo
            , HttpServletRequest request) {
        return userService.insert(dto, photo, request);
    }

    @GetMapping("deleteUser")
    public ModelAndView deleteUser(@NotBlank String id) {
        return userService.delete(id);
    }

    @ResponseBody
    @PostMapping("/idCheck")
    public Map<String, String> idCheck(@NotBlank String id) {
        return userService.idCheck(id);
    }
}
