package com.billlog.rest.salsapan.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web/common")
public class WebCommonController {

    /**
     * 이메일 인증 완료 페이지
     */
    @GetMapping(value = "/emailConfirm")
    public ModelAndView adminLoginPage(Model model, ModelAndView mav,
                                       @RequestParam("autyType") String type) {

        model.addAttribute("authType", type);
        mav.setViewName("web/common/emailConfirm");
        return mav;
    }
}
