package com.example.shipgofunding.User;

import com.example.shipgofunding.UserDetatilService.SinupRequest;
import com.example.shipgofunding.UserDetatilService.UserService;
import com.example.shipgofunding.utils.ApiResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String SUCCESS_STATUS = "success";



    //TO DO: 공통응답클래스로 수정
    // 유저 정보 저장
    @PostMapping("/user")
    public ApiResponseBuilder.ApiResponse<String> signup (SinupRequest userRequest) {
        userService.save(userRequest);
        return new ApiResponseBuilder.ApiResponse<String>(SUCCESS_STATUS, null, null);

    }

  //TO DO: 공통응답 클래스 수정 및 코드 수정
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login.html";
    }

    //TO DO: View 제거 , 로그인/회원가입 기능
    @GetMapping("/login")

    @PostMapping("/signup")

}
