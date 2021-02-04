package com.exercise.javas.controller;

import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.repository.BoardRepository;
import com.exercise.javas.service.BoardService;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestPropertySource("/application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Validator validator;

    static private MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    static BoardDTO dto;
    @Test
    @Order(1)
    @DisplayName("dto insert")
    void T1() throws Exception {

        map.add("id","boxak");
        map.add("name","황지원");
        map.add("title","컨트롤러 테스트");
        map.add("content","컨트롤러 테스트입니다");
        map.add("date",JavasUtils.getTodayString("YYYY-MM-dd HH:mm:ss"));
        map.add("hit","0");
        map.add("reviewCnt","0");
        map.add("boardType","jobad");

        mockMvc.perform(post("/board/post")
                        .params(map)
                        .param("action","insert")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Order(2)
    @DisplayName("validation 체크")
    @Test
    void T2(){
        dto = new BoardDTO();

        dto.setUserId("boxak");
        dto.setBoardType("jobad");
        dto.setReviewCnt(0);
        dto.setDate(JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));
        dto.setContent("유효성 검사 테스트입니다!");
        dto.setTitle("");
        dto.setName("Hwang Jiwon");
        dto.setHit(0);

        Set<ConstraintViolation<BoardDTO>> validate = validator.validate(dto);
        Iterator<ConstraintViolation<BoardDTO>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while(iterator.hasNext()) {
            ConstraintViolation<BoardDTO> next = iterator.next();
            messages.add(next.getMessage());
            log.info("messages = {}",next.getMessage());
        }
    }
}
