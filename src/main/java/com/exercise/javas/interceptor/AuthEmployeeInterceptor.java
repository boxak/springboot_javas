package com.exercise.javas.interceptor;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.utils.JavasConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthEmployeeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDTO dto = (UserDTO) session.getAttribute(JavasConstants.LOGIN_DTO);
        if (dto!=null) {
            if ("wantad".equals(dto.getUserType())) {
                return true;
            } else {
                response.sendRedirect("/javas/noticeResult?type=unauthorized");
                return false;
            }
        } else {
            response.sendRedirect("/");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView mav) throws Exception {
        super.postHandle(request, response, handler, mav);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
