package com.exercise.javas.interceptor;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.utils.JavasConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession();
    UserDTO dto = (UserDTO) session.getAttribute(JavasConstants.LOGIN_DTO);
    if (dto != null) {
      return true;
    } else {
      response.sendRedirect("/javas/authloginfail");
      return false;
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler, ModelAndView mav) throws Exception {
    super.postHandle(request, response, handler, mav);
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    super.afterCompletion(request, response, handler, ex);
  }
}
