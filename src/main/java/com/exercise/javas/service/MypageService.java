package com.exercise.javas.service;

import com.exercise.javas.dto.ReviewDTO;
import com.exercise.javas.repository.ReviewRepository;
import com.exercise.javas.repository.UserRepository;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import com.exercise.javas.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MypageService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public ModelAndView mypage(HttpSession session) {
        ModelAndView mav = new ModelAndView(JavasConstants.MYPAGE);
        UserDTO dto = (UserDTO) session.getAttribute(JavasConstants.LOGIN_DTO);
        mav.addObject(JavasConstants.LOGIN_DTO, dto);
        return mav;
    }

    public ModelAndView modifymeminfo(HttpSession session) {
        ModelAndView mav = new ModelAndView(JavasConstants.MEMINFO_MODIFY);
        UserDTO dto = (UserDTO) session.getAttribute(JavasConstants.LOGIN_DTO);
        mav.addObject(JavasConstants.LOGIN_DTO, dto);
        return mav;
    }

    public ModelAndView meminfoupdate(UserDTO dto, MultipartFile photo, HttpSession session, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        mav.addObject("Type", "meminfoupdate");
        try {
            userRepository.save(dto);
            JavasUtils.uploadPhoto(dto.getId(), photo, request);
            mav.addObject(JavasConstants.MSG, "수정에 성공했습니다");
            session.setAttribute(JavasConstants.LOGIN_DTO, dto);
        } catch (Exception e) {
            log.warn(JavasUtils.messegingExLog(e.toString(),e.getMessage()));
            mav.addObject(JavasConstants.MSG, "수정에 실패했습니다!");
        }
        return mav;
    }

    public String myreviews(HttpSession session) throws JsonProcessingException {
        String userId = ((UserDTO)session.getAttribute(JavasConstants.LOGIN_DTO)).getId();
        List<ReviewDTO> list = new ArrayList<>();
        try {
            list = reviewRepository.findAllByTargetId(userId);
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        return new ObjectMapper().writeValueAsString(list);
    }
}
