package com.exercise.javas.service;

import com.exercise.javas.dto.UserDTO;
import com.exercise.javas.repository.FindRepository;
import com.exercise.javas.utils.JavasConstants;
import com.exercise.javas.utils.JavasUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class FindService {
    @Autowired
    FindRepository findRepository;

    public ModelAndView findID(String name, String phone) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        String message = "";
        try {
            if (!findRepository.existsByName(name)) {
                message = "해당하는 이름이 없습니다!";
            } else if (!findRepository.existsByNameAndPhone(name, phone)) {
                message = "이름과 휴대폰 번호가 일치하지 않습니다!";
            } else {
                message = "귀하의 아이디는 " + findRepository.findFirstByNameAndPhone(name, phone).getId()
                        + " 입니다. 로그인 화면으로 돌아갑니다.";
            }
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        mav.addObject(JavasConstants.MSG, message);
        return mav;
    }

    public ModelAndView findPW(String id, String name, String email) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        String result = "";
        try {
            UserDTO dto = findRepository.findFirstByIdAndNameAndEmail(id, name, email);
            if (dto == null) {
                result = "해당하는 정보가 없습니다. 한번 더 확인을 해주세요!";
            } else {
                String subject = "잉력시장입니다. 고객님의 비밀번호를 재설정하세요.";
                String body = "<h3>다음 링크를 클릭하시면 고객님의 비밀번호 재설정하는 화면으로 이동합니다.<h3>"
                        + "<div>"
                        + "<a href='http://localhost:8000/javas/resetPwForm?email=" + email + "'>이동</a>"
                        + "</div>";
                JavasUtils.sendEmail(email, subject, body);
                result = "비밀번호를 메일로 보내드렸습니다.\\n\\메일함을 확인해주세요.";
            }
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        mav.addObject(JavasConstants.MSG, result);
        return mav;
    }

    public ModelAndView resetPwForm(String email) {
        ModelAndView mav = new ModelAndView("resetPwForm");
        mav.addObject("email", email);
        return mav;
    }

    public String resetPw(String email, String password) {
        String result = "";
        try {
            UserDTO dto = findRepository.findFirstByEmail(email);
            dto.setPassword(password);
            findRepository.save(dto);
            result = "success";
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
            result = "fail";
        }
        return result;
    }
}
