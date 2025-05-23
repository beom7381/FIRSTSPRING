package com.example.firstproject.controller;

import com.example.firstproject.dto.LoginRequestDto;
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

}