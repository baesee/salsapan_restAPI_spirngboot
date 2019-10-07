package com.billlog.rest.salsapan.web.admin.controller;

import com.billlog.rest.salsapan.advice.exception.CEmailSigninFailedException;
import com.billlog.rest.salsapan.config.security.JwtTokenProvider;
import com.billlog.rest.salsapan.mapper.UserMapper;
import com.billlog.rest.salsapan.model.SalsaUser;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/web/admin")
public class AdminSignController {

    @Autowired
    private UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    public AdminSignController(JwtTokenProvider jwtTokenProvider, ResponseService responseService, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 관리자 페이지 로그인 화면이동
     */
    @GetMapping(value = "/login")
    public ModelAndView adminLoginPage(Model model, ModelAndView mav, @ModelAttribute SalsaUser salsaUser) {
        model.addAttribute("errMsg", "11111111111111");
        model.addAttribute("serverTime", new Date());

        mav.setViewName("web/login");
        return mav;
    }

    /**
     * 프리미엄 신청 사용자 목록을 보여줄 화면
     */
    @GetMapping(value = "/pusers/confirm")
    public ModelAndView premiumWatingUsers(Model model, ModelAndView mav, @ModelAttribute SalsaUser salsaUser) {

        List<SalsaUser> users = userMapper.getAll("P","W");

        model.addAttribute("users", users);

        mav.setViewName("web/users");
        return mav;
    }

   /* // 로그인 화면에서 받은 id , pw 값을 이용한 로그인 처리.
    @PostMapping(value = "/login")
    public ModelAndView adminLoginRegister(Model model, ModelAndView mav, @Valid SalsaUser salsaUser) {

        System.err.println(" ================= WEB >> POST login");

        SalsaUser user = userMapper.findByUsername(salsaUser.getUser_id());

        //아이디가 없을 경우.
        if(CustomUtils.isEmpty(user)){
            model.addAttribute("errMsg","존재하지 않는 아이디 입니다.");
            mav.setViewName("redirect:/web/admin/login");
        }else {
            //패스워드가 다를 경우.
            if (!passwordEncoder.matches(salsaUser.getUser_pwd(), user.getUser_pwd())) {
                model.addAttribute("errMsg","패스워드가 다릅니다.");
                mav.setViewName("redirect:/web/admin/login");
            } else {
                //로그인 성공
                mav.setViewName("web/main");
            }
        }

        return mav;
    }*/


    // 로그인 화면에서 받은 id , pw 값을 이용한 로그인 처리.
    @PostMapping(value = "/login")
    public String adminLoginRegister(Model model, ModelAndView mav, @Valid SalsaUser salsaUser) {

        String url = "";

        SalsaUser user = userMapper.findByUsername(salsaUser.getUser_id());

        //아이디가 없을 경우.
        if(CustomUtils.isEmpty(user)){

            url = "redirect:/web/admin/login";
//            mav.setViewName("redirect:/web/admin/login");
        }else {
            //패스워드가 다를 경우.
            if (!passwordEncoder.matches(salsaUser.getUser_pwd(), user.getUser_pwd())) {
                model.addAttribute("errMsg","패스워드가 다릅니다.");
                url = "redirect:/web/admin/login";
//                mav.setViewName("redirect:/web/admin/login");
            } else {
                //로그인 성공
                url = "web/main";
//                mav.setViewName("web/main");
            }
        }
        model.addAttribute("errMsg", "aaaaaaaaaaakkkdkdkdkdkdkdkdkdkdk");
        model.addAttribute("serverTime", new Date());

        return url;
    }


//
//    public class IndexController {
//        @GetMapping("/")
//        public String home(Model model){
//            model.addAttribute("message", "hello world");
//            return "index";
//        }

}
