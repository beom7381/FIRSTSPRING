package com.example.firstproject.controller;

import com.example.firstproject.dto.LoginRequestDto;
import com.example.firstproject.dto.RegistRequestDto;
import com.example.firstproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {
    private final UserService loginService;

    public UserController(UserService userService){
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
    public String requestLogin(Model model, LoginRequestDto loginRequestDto, HttpSession session){
        var login = loginRequestDto.toEntity();

        if(loginService.tryLogin(login)){
            log.info("Login Success : " + login.toString());
            model.addAttribute("loginRequestDto", loginRequestDto);
            session.setAttribute("login_id", loginRequestDto.getLoginId());
            return "redirect:/home";
        }
        else {
            log.info("Login Failed : " + login.toString());
            model.addAttribute("errorMessage", "아이디나 비밀번호가 틀렸습니다.");
            session.setAttribute("login_id", null);
            return "member/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/";
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
