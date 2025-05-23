package com.example.firstproject.controller;

import com.example.firstproject.dto.LoginRequestDto;
import com.example.firstproject.dto.RegistRequestDto;
import com.example.firstproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    private final UserService loginService;

    public MemberController(UserService userService){
        this.loginService = userService;
    }

    @GetMapping("/login")
    public String toLoginPage(Model model){
        model.addAttribute("errorMessage", "");
        return "member/login";
    }

    @GetMapping("/signup")
    public String toSignUpPage(Model model){
        model.addAttribute("errorMessage", "");
        return "member/signup";
    }

    @PostMapping("/login")
    public String requestLogin(Model model, LoginRequestDto loginRequestDto){
        var login = loginRequestDto.toEntity();

        log.info(login.toString());
        if(loginService.tryLogin(login)){
            model.addAttribute("loginRequestDto", loginRequestDto);
            return "greetings";
        }
        else {
            model.addAttribute("errorMessage", "아이디나 비밀번호가 틀렸습니다.");
            return "member/login";
        }
    }

    @PostMapping("/signup")
    public String requestSignUp(Model model, RegistRequestDto registRequestDto){
        var register = registRequestDto.toEntity();

        if(loginService.tryRegister(register)){
            model.addAttribute("errorMessage", "");
            return "member/login";
        }
        else {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
            return "member/signup";
        }
    }
}
