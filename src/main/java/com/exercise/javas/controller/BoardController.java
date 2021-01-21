package com.exercise.javas.controller;

import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.service.BoardService;
import com.exercise.javas.utils.JavasConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
                             @RequestParam(value = JavasConstants.TYPE, required = false) String type,
                             HttpSession session) {
        return boardService.list(boardType, pgNum, key, type, session);
    }

    @GetMapping("/board/listOne")
    public ModelAndView listOne(String postId, HttpSession session) {
        return boardService.listOne(postId, session);
    }

    @GetMapping("/board/{boardType}/{action}Form")
    public ModelAndView insertForm(@PathVariable String action,
                                   @PathVariable String boardType) {
        return new ModelAndView("board" + action + "Form")
                .addObject(JavasConstants.BOARD_TYPE, boardType);
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
