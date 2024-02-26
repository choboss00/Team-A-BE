package com.example.shipgofunding.Controller;

import com.example.shipgofunding.UserDetatilService.UserDetailservice;
import com.example.shipgofunding.UserDetatilService.UserRequest;
import com.example.shipgofunding.UserDetatilService.UserService;
import com.example.shipgofunding.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    //로그인 폼
    @RequestMapping("static/login")
    public String login() {
        return "login";
    }
    //회원가입 폼
    @PostMapping("static/signup")
    public String signup(UserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

}



