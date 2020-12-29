package com.exercise.javas.controller;

import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.service.BoardService;
import com.exercise.javas.utils.JavasConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/board/{boardType}")
    public ModelAndView list(@PathVariable @NotNull String boardType,
                             @RequestParam(defaultValue = "1") int pgNum,
                             @RequestParam(value = JavasConstants.KEY, required = false) String key,
                             @RequestParam(value = JavasConstants.TYPE, required = false) String type) {
        return boardService.list(boardType, pgNum, key, type);
    }

    @GetMapping("/board/listOne")
    public ModelAndView listOne(String postId) {
        return boardService.listOne(postId);
    }

    @GetMapping("/board/{action}Form")
    public String insertForm(@PathVariable String action) {
        return "board" + action + "Form";
    }

    @GetMapping("/board/delete")
    public ModelAndView delete(String postId) {
        return boardService.delete(postId);
    }

    @PostMapping("/board/post")
    public ModelAndView doPost(String action, @Valid BoardDTO dto) {
        return boardService.doPost(action, dto);
    }
}
