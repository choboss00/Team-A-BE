package com.example.shipgofunding.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import com.example.shipgofunding.UserDetatilService.UserRequest;
import com.example.shipgofunding.UserDetatilService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
// 유저 정보 저장
    @PostMapping("/user")
    public String signup(UserRequest request) {
        userService.save(request);  // 회원 가입 메서드 호출
        return "redirect:/login.html";  // 회원 가입이 완료된 이후에 로그인 페이지로 이동
    }




    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
