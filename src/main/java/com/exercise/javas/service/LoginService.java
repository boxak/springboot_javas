package com.exercise.javas.service;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.repository.LoginRepository;
import com.exercise.javas.utils.JavasConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;

    public ModelAndView login(String id, String password, String isSaveId,
                              HttpSession session, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        UserDTO dto = new UserDTO();
        boolean hasId = loginRepository.existsById(id);
        if (!hasId) {
            mav.setViewName(JavasConstants.NOTICE_RESULT);
            mav.addObject(JavasConstants.MSG_TYPE, "login");
            mav.addObject(JavasConstants.MSG, "존재하지 않는 아이디입니다!");
        } else {
            dto = loginRepository.findFirstByIdAndPassword(id, password);
            if (dto == null) {
                mav.setViewName(JavasConstants.NOTICE_RESULT);
                mav.addObject(JavasConstants.MSG_TYPE,"login");
                mav.addObject(JavasConstants.MSG, "비밀번호가 일치하지 않습니다!");
            } else {
                session.setAttribute(JavasConstants.LOGIN_DTO,dto);
                if (isSaveId != null) {
                    Cookie cookie = new Cookie("saveId", id);
                    cookie.setMaxAge(60*60*24*7);
                    response.addCookie(cookie);
                }
                mav.setViewName("redirect:afterlogin");
            }
        }
        return mav;
    }
}
