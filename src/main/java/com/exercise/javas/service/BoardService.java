package com.exercise.javas.service;

import com.exercise.javas.dto.BoardDTO;
import com.exercise.javas.repository.BoardRepository;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    private static final int POST_CNT_PER_PAGE = 9;
    private static final int PAGE_UNIT = 5;

    public ModelAndView list(String boardType, int pgNum, String key, String searchType,HttpSession session) {
        ModelAndView mav = new ModelAndView(JavasConstants.BOARD_VIEW);

        List<BoardDTO> boardList = new ArrayList<>();
        long totalPostCnt = 0;
        pgNum = pgNum - 1;
        if (StringUtils.isEmpty(searchType) || StringUtils.isEmpty(key)) {
            totalPostCnt = boardRepository.countAllByBoardType(boardType);
            Pageable page = null;
            if (totalPostCnt > 0) {
                page = PageRequest.of(pgNum, POST_CNT_PER_PAGE > totalPostCnt ? (int) totalPostCnt : POST_CNT_PER_PAGE);
            } else page = Pageable.unpaged();
            boardList = boardRepository.findAllByBoardType(boardType, page);
        } else if ("title_content".equals(searchType)) {
            totalPostCnt = boardRepository.countAllByBoardTypeAndTitleContainsOrContentContains(boardType, key);
            Pageable page = null;
            if (totalPostCnt > 0) {
                page = PageRequest.of(pgNum, POST_CNT_PER_PAGE > totalPostCnt ? (int) totalPostCnt : POST_CNT_PER_PAGE);
            } else page = Pageable.unpaged();
            boardList = boardRepository.findAllByBoardTypeAndTitleContainsOrContentContains(boardType, key, page);
        } else if ("title".equals(searchType)) {
            totalPostCnt = boardRepository.countAllByBoardTypeAndTitleContains(boardType, key);
            Pageable page = null;
            if (totalPostCnt > 0) {
                page = PageRequest.of(pgNum, POST_CNT_PER_PAGE > totalPostCnt ? (int) totalPostCnt : POST_CNT_PER_PAGE);
            } else page = Pageable.unpaged();
            boardList = boardRepository.findAllByBoardTypeAndTitleContains(boardType, key, page);
        } else if ("content".equals(searchType)) {
            totalPostCnt = boardRepository.countAllByBoardTypeAndContentContains(boardType, key);
            Pageable page = null;
            if (totalPostCnt > 0) {
                page = PageRequest.of(pgNum, POST_CNT_PER_PAGE > totalPostCnt ? (int) totalPostCnt : POST_CNT_PER_PAGE);
            } else page = Pageable.unpaged();
            boardList = boardRepository.findAllByBoardTypeAndContentContains(boardType, key, page);
        } else if("id".equals(searchType)) {
            totalPostCnt = boardRepository.countAllByBoardTypeAndUserId(boardType, key);
            Pageable page = null;
            if (totalPostCnt > 0) {
                page = PageRequest.of(pgNum, POST_CNT_PER_PAGE > totalPostCnt ? (int) totalPostCnt : POST_CNT_PER_PAGE);
            } else page = Pageable.unpaged();
            boardList = boardRepository.findAllByBoardTypeAndUserId(boardType, key, page);
        }

        String linkStr = "";
        mav.addObject("boardTypeKor", boardType.equals("jobad") ? "구인 게시판" : "구직 게시판");
        if (!CollectionUtils.isEmpty(boardList)) {
            mav.addObject("boardList", boardList);
        }

        if (!StringUtils.isEmpty(searchType)) {
            if (CollectionUtils.isEmpty(boardList)) {
                mav.addObject("nullErrorMsg", key + "을(를) 포함하는 검색글이 없습니다.");
            } else {
                linkStr = "&type=" + searchType + "&key" + key;
            }
        }

        String pagelist = JavasUtils.getPageLinkList(boardType, pgNum, linkStr, totalPostCnt);
        mav.addObject("pagelist",pagelist);

        if (!StringUtils.isEmpty(key)) {
            session.setAttribute(JavasConstants.KEY, key);
        }
        if (!StringUtils.isEmpty(searchType)) {
            session.setAttribute(JavasConstants.TYPE,searchType);
        }
        session.setAttribute(JavasConstants.BOARD_TYPE,boardType);
        session.setAttribute(JavasConstants.PG_NUM, pgNum+1);
        session.setAttribute(JavasConstants.BOARD_TYPE_KOR, boardType.equals(JavasConstants.JOBAD) ? "구인 내용" : "구직 내용");

        return mav;
    }

    public ModelAndView listOne(String postId,HttpSession session) {
        ModelAndView mav = new ModelAndView(JavasConstants.BOARD_VIEW);

        BoardDTO dto = boardRepository.findById(postId).orElse(new BoardDTO());
        if (dto != null) {
            String boardType = dto.getBoardType();
            int hit = dto.getHit();
            dto.setHit(hit + 1);
            boardRepository.save(dto);
            session.setAttribute(JavasConstants.LIST_ONE, dto);
            mav.addObject(JavasConstants.DTO, dto);
        }
        return mav;
    }

    public ModelAndView delete(String postId) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        try {
            boardRepository.deleteById(postId);
            mav.addObject(JavasConstants.MSG, "삭제에 성공했습니다.");
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
            mav.addObject(JavasConstants.MSG, "삭제에 실패했습니다.");
        }
        return mav;
    }

    public ModelAndView doPost(String action, BoardDTO dto) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        String actionKor = "insert".equals(action) ? "삽입" : "수정";
        String result = "";
        try {
            boardRepository.save(dto);
            result = "성공";
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
            result = "실패";
        }
        String msg = actionKor + "에 " + result + "하였습니다.";
        mav.addObject(JavasConstants.MSG, msg);
        mav.addObject(JavasConstants.BOARD_TYPE, dto.getBoardType());
        return mav;
    }
}
