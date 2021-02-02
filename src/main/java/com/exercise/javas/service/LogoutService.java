package com.exercise.javas.service;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.utils.JavasConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Service
public class LogoutService {
    public ModelAndView logout(HttpSession session) {
        UserDTO dto = (UserDTO) session.getAttribute(JavasConstants.LOGIN_DTO);
        if (dto != null) {
            session.removeAttribute(JavasConstants.LOGIN_DTO);
        }
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        mav.addObject(JavasConstants.MSG_TYPE,"logoutSuccess");
        return mav;
    }
}
