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
        mav.addObject(JavasConstants.MSG_TYPE, "findID");
        String message = "";
        try {
            if(!findRepository.existsByName(name)) {
                message = "noName";
            } else if (!findRepository.existsByNameAndPhone(name, phone)) {
                message = "notMatchedPhone";
            } else {
                message = findRepository.findFirstByNameAndPhone(name, phone).getId();
            }
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        mav.addObject(JavasConstants.MSG, message);
        return mav;
    }

    public ModelAndView findPW(String id, String name, String email) {
        ModelAndView mav = new ModelAndView(JavasConstants.NOTICE_RESULT);
        mav.addObject(JavasConstants.MSG_TYPE, "findPW");
        String result = "";
        try {
            UserDTO dto = findRepository.findFirstByIdAndNameAndEmail(id,name,email);
            if (dto == null) {
                result = "fail";
            } else result = "success";
        } catch (Exception e) {
            JavasUtils.messegingExLog(e.toString(), e.getMessage());
        }
        mav.addObject(JavasConstants.MSG,result);
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
            UserDTO dto = findRepository.findFirstByEmailAndPassword(email, password);
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
