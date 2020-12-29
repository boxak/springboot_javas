package com.exercise.javas.repository;

import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@DataMongoTest
@TestPropertySource("/application_test.properties")
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    static BoardDTO dto;
    static final int TEST_DATA_CNT = 100;

    @Order(1)
    @DisplayName("컬렉션 초기화")
    @Test
    void T1() {
        boardRepository.deleteAll();
        Assertions.assertThat(boardRepository.count()).isEqualTo(0);
    }

    @Order(2)
    @DisplayName("게시글 100개 삽입 테스트")
    @Test
    void T2() {
        for (int i = 1; i <= TEST_DATA_CNT; i++) {
            dto = new BoardDTO();
            dto.setName("황지원");
            dto.setTitle("게시글 테스트"+i);
            dto.setHit(0);
            dto.setContent("테스트입니다.");
            dto.setDate(JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));
            dto.setReviewCnt(0);
            dto.setBoardType("jobad");
            if (i <= 50) {
                dto.setId("jidide");
            } else {
                dto.setId("boxak");
            }
            boardRepository.save(dto);
            log.info(dto.toString());
        }
        Assertions.assertThat(boardRepository.count()).isEqualTo(TEST_DATA_CNT);
    }

    @Order(3)
    @DisplayName("게시글 수정 테스트")
    @Test
    void T3() {
        List<BoardDTO> list = boardRepository.findAll();
        for (int i = 1; i <= TEST_DATA_CNT; i++) {
            BoardDTO dto = list.get(i - 1);
            if (i % 2 == 0) {
                dto.setBoardType("wantad");
                boardRepository.save(dto);
            }
        }

        list = boardRepository.findAll();

        for (int i = 1; i <= TEST_DATA_CNT; i++) {
            if (i % 2 == 0) Assertions.assertThat(list.get(i - 1).getBoardType()).isEqualTo("wantad");
            log.info(boardRepository.findAll().get(i - 1).toString());
        }

    }

    @Order(4)
    @DisplayName("boardType에 따라 데이터 불러오기")
    @Test
    void T4() {
        List<BoardDTO> list = boardRepository.findAllByBoardType("jobad", Pageable.unpaged());
        Assertions.assertThat(list.size()).isEqualTo(TEST_DATA_CNT / 2);
    }

    @Order(5)
    @DisplayName("페이징 테스트")
    @Test
    void T5() {
        Pageable page = PageRequest.of(1, 5);
        List<BoardDTO> list = boardRepository.findAllByBoardType("jobad", page);
        for (BoardDTO dto : list) {
            log.info(dto.toString());
        }
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("게시글 테스트11");
    }

    @Order(6)
    @DisplayName("아이디로 게시글 조회 테스트")
    @Test
    void T6() {
        Pageable page = PageRequest.of(1, 10);
        List<BoardDTO> list = boardRepository.findAllByBoardTypeAndId("jobad", "jidide", page);
        for (BoardDTO dto : list) {
            log.info(dto.toString());
        }
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("게시글 테스트21");
    }

    @Order(7)
    @DisplayName("제목 및 내용 키워드로 게시글 조회 테스트")
    @Test
    void T7() {
        List<BoardDTO> list1 = boardRepository.findAllByBoardTypeAndTitleContainsOrContentContains("wantad","4",Pageable.unpaged());
        List<BoardDTO> list2 = boardRepository.findAllByBoardTypeAndTitleContainsOrContentContains("wantad","게시글",Pageable.unpaged());
        log.info(list1.get(0).toString());
        Assertions.assertThat(list2.size()).isEqualTo(50);
    }

    @Order(8)
    @DisplayName("제목으로 게시글 조회 테스트")
    @Test
    void T8() {
        BoardDTO dto = new BoardDTO();

        dto.setId("boxak30134");
        dto.setBoardType("jobad");
        dto.setReviewCnt(0);
        dto.setDate(JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));
        dto.setContent("제목으로 게시글 조회 테스트");
        dto.setTitle("제목테스트");
        dto.setName("황창원");
        dto.setHit(0);
        boardRepository.save(dto);

        List<BoardDTO> list1 = boardRepository.findAllByBoardTypeAndTitleContains("jobad","제목",Pageable.unpaged());
        List<BoardDTO> list2 = boardRepository.findAllByBoardTypeAndTitleContains("wantad","제목",Pageable.unpaged());

        Assertions.assertThat(list1.size()).isEqualTo(1);
        Assertions.assertThat(list2.size()).isEqualTo(0);
    }

    @Order(9)
    @DisplayName("내용으로 게시글 조회 테스트")
    @Test
    void T9() {
        dto = new BoardDTO();

        dto.setId("boxak30134");
        dto.setBoardType("wantad");
        dto.setReviewCnt(0);
        dto.setDate(JavasUtils.getTodayString(JavasConstants.COMMON_DATE_FORMAT));
        dto.setContent("내용으로 게시글 조회 테스트");
        dto.setTitle("내용테스트");
        dto.setName("황창원");
        dto.setHit(0);

        List<BoardDTO> list1 = boardRepository.findAllByBoardTypeAndTitleContains("wantad","내용",Pageable.unpaged());
        List<BoardDTO> list2 = boardRepository.findAllByBoardTypeAndTitleContains("jobad","내용",Pageable.unpaged());

        Assertions.assertThat(list1.size()).isEqualTo(1);
        Assertions.assertThat(list2.size()).isEqualTo(0);
    }
}
