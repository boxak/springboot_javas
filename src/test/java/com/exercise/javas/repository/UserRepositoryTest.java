package com.exercise.javas.repository;

import com.exercise.javas.config.MongoConfig;
import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@DataMongoTest
@TestPropertySource("/application_test.properties")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static UserDTO dto;

    static int num = 0;

    @Order(1)
    @DisplayName("DTO 세팅 및 컬렉션 초기화")
    @Test
    void T1() {
        num += 1;
        log.info((num) + "");

        dto.setId("boxak");
        dto.setName("황지원");
        dto.setPassword("ab6606");
        dto.setPhone("010-0011-1122");
        dto.setUserType("admin");
        dto.setEmail("boxak@naver.com");
        dto.setSex("male");
        dto.setBirthday("1995-02-04");

        userRepository.deleteAll();
        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("30개 데이터 입력")
    @Order(2)
    @Test
    void T2() {
        num += 1;
        log.info((num) + "");
        for (int i = 1; i <= 30; i++) {
            dto.setId("boxak" + i);
            userRepository.save(dto);
        }

        Assertions.assertThat(userRepository.count()).isEqualTo(30);
    }

    @DisplayName("처음 10개 데이터 업데이트")
    @Order(3)
    @Test
    void T3() {
        num += 1;
        log.info((num) + "");
        for (int i = 1; i <= 10; i++) {
            dto.setId("boxak" + i);
            dto.setUserType("jobad");
            userRepository.save(dto);
        }
        Assertions.assertThat(userRepository.countAllByUserType("jobad")).isEqualTo(10);
    }

    @DisplayName("find 메서드 테스트")
    @Order(4)
    @Test
    void T4() {
        num += 1;
        log.info("find" + (num) + "");
        for (int i = 1; i <= 30; i++) {
            boolean result = userRepository.existsById("boxak" + i);
            Assertions.assertThat(result).isTrue();
        }
    }

    @DisplayName("데이터 삭제 테스트")
    @Order(5)
    @Test
    void T5() {
        num += 1;
        log.info("delete" + (num++) + "");
        userRepository.deleteAllByUserType("jobad");
        Assertions.assertThat(userRepository.count()).isEqualTo(20);
    }
}
