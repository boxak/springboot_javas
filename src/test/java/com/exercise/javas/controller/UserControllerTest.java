package com.exercise.javas.controller;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestPropertySource("/application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static UserDTO dto;
    static MockMultipartFile photo;
    static String content;
    static MultiValueMap<String, String> map;

    @Test
    @Order(1)
    @DisplayName("MockMvc 설정")
    void T1() throws IOException {

        mockMvc = MockMvcBuilders.standaloneSetup(UserController.class)
                .alwaysExpect(status().isOk())
                .build();

        map = new LinkedMultiValueMap<>();
        map.add("id", "woo1234");
        map.add("name", "우기");
        map.add("birthday", "1995-02-04");
        map.add("sex", "female");
        map.add("address", "서울특별시 성동구");
        map.add("email", "woogi@naver.com");
        map.add("phone", "010-2222-5454");
        map.add("userType", "wantad");
        map.add("password", "ab1234!!");
        map.add("date", JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));

        content = new ObjectMapper().writeValueAsString(dto);
        System.out.println(content);
        String photo_path = "C:\\Users\\Administrator\\Documents\\yeji.png";
        File f = new File(photo_path);

        photo = new MockMultipartFile("예지.png", new FileInputStream(f));
    }

    @Test
    @Order(2)
    @DisplayName("회원가입 테스트")
    void T2() throws Exception {
        log.info("");
        mockMvc.perform(post("/signin.do")
                .params(map)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(multipart("/signin.do")
                .file(photo))
                .andExpect(status().isOk())
                .andDo(print());

    }
}
