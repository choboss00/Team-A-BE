package com.example.shipgofunding.User;

import com.example.shipgofunding.UserDetatilService.UserService;
import com.example.shipgofunding.domain.User;
import com.example.shipgofunding.utils.ApiResponseBuilder;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shipgofunding.UserDetatilService.SignupRequest;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String SUCCESS_STATUS = "success";


    //TO DO: 공통응답클래스로 수정
    // 회원가입 유저 정보 저장
    @PostMapping("/signup")
    public String signup(SignupRequest signupRequest) {
        userService.save(signupRequest);
        return String.valueOf(new ApiResponseBuilder.ApiResponse <> (SUCCESS_STATUS, null, null));
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return String.valueOf(new ApiResponseBuilder.ApiResponse<String>(SUCCESS_STATUS, null, null));
    }

    //로그인 정보 반환
    @RequestMapping("/login")
    public String login(SignupRequest userRequest, Authentication authentication) throws JsonProcessingException {
        // 현재 사용자의 인증 정보를 가져옴
            User data = (User) authentication.getPrincipal();
        return String.valueOf(new ApiResponseBuilder.ApiResponse(SUCCESS_STATUS, data, null));
    }


}