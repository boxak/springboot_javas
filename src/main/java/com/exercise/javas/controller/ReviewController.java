package com.exercise.javas.controller;

import com.exercise.javas.dto.ReviewDTO;
import com.exercise.javas.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @ResponseBody
    @GetMapping(value = "/review", produces = "application/json; charset=UTF-8")
    public String reviewList(String postId) throws JsonProcessingException {
        return reviewService.reviewList(postId);
    }

    @ResponseBody
    @PostMapping(value = "/review/insert", produces = "application/json; charset=UTF-8")
    public String insert(ReviewDTO dto,String postId) {
        return reviewService.insert(dto);
    }

    @ResponseBody
    @PostMapping(value = "/review/update", produces = "application/json; charset=UTF-8")
    public String update(ReviewDTO dto, String postId) {
        return reviewService.update(dto);
    }

    @ResponseBody
    @GetMapping(value = "/review/delete", produces = "application/json; charset=UTF-8")
    public String delete(String postId, String reviewId) {
        return reviewService.delete(reviewId);
    }
}
