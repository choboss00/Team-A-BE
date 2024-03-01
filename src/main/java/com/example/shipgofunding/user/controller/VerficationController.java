package com.example.shipgofunding.user.controller;

import com.example.shipgofunding.config.Redis.RedisUtils;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.user.request.UserRequest;
import com.example.shipgofunding.user.service.PasswordVerfication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class VerficationController {

    @Autowired
    private PasswordVerfication passwordVerfication;
    private PrincipalUserDetails principalUserDetails;
    //사용자 이메일 가져오기
    String email = principalUserDetails.getUsername();

    @Operation(summary = "이메일 인증 코드", description = "이메일로 인증 코드를 보냅니다.")
    @ApiResponse(responseCode = "200", description = "인증코드 전송 성공")
    @PostMapping("/")
    public ResponseEntity<?> signup(Errors errors) throws MessagingException {
        passwordVerfication.sendMail(email);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }



}


