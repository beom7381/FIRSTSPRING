package com.example.firstproject.controller;

import com.example.firstproject.dto.LoginRequestDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/")
    public String niceToMeetYou(Model model, LoginRequestDto loginRequestDto){
        model.addAttribute("errorMessage", "");
        return "member/login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session){
        String loginid = (String)session.getAttribute("login_id");

        if(loginid != null){
            model.addAttribute("loginId", loginid);

            return "greetings";
        }
        else{
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "member/login";
        }
    }
}