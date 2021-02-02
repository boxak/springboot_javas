package com.exercise.javas.service;

import com.exercise.javas.dto.ReviewDTO;
import com.exercise.javas.repository.ReviewRepository;
import com.exercise.javas.utils.JavasUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public String reviewList (String postId) throws JsonProcessingException {
        List<ReviewDTO> reviewList = new ArrayList<>();
        try {
            reviewList = reviewRepository.findAllByPostId(postId);
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        return new ObjectMapper().writeValueAsString(reviewList);
    }

    public String myReview(String userId) throws JsonProcessingException {
        List<ReviewDTO> reviewList = new ArrayList<>();
        try {
            reviewList = reviewRepository.findAllByTargetId(userId);
        } catch(Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        return new ObjectMapper().writeValueAsString(reviewList);
    }

    public String insert(ReviewDTO dto) {
        String result = "";
        try {
            reviewRepository.save(dto);
            result = "success";
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(),e.getMessage());
            result = "fail";
        }
        return result;
    }

    public String update(ReviewDTO dto) {
        String result = "";
        try {
            reviewRepository.save(dto);
            result = "success";
        } catch(Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
            result = "fail";
        }
        return result;
    }

    public String delete(String reviewId) {
        String result = "";
        try {
            reviewRepository.deleteById(reviewId);
            result = "success";
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
            result = "fail";
        }
        return result;
    }
}
