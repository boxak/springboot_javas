package com.exercise.javas.service;

import com.exercise.javas.dto.ReviewDTO;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@DataMongoTest
@TestPropertySource("/application_test.properties")
@ComponentScan("com.exercise.javas.service.ReviewService")
public class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    static ReviewDTO dto;

    @Test
    @DisplayName("리뷰 적기 테스트")
    void T1() {
        for (int i = 0;i<5;i++) {
            dto.setReviewerId("jidide"+i);
            dto.setComment("진심입니다.");
            dto.setDate(JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));
            dto.setRate(3.0);
            reviewService.insert(dto);
        }
    }
}
