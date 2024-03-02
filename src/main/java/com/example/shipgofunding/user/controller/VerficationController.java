package com.example.shipgofunding.user.controller;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest;
import com.example.shipgofunding.user.service.SendVerficationcode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shipgofunding.user.request.UserRequest.VerficationDTO;

import java.util.Map;

@Tag(name = "User", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VerficationController {

    @Autowired
    private SendVerficationcode sendVerficationcode;
    private UserRepository userRepository;


    @Operation(summary = "이메일 인증 코드 전송", description = "이메일로 인증 코드를 보냅니다.")
    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공")
    @PostMapping("/forgot-password/sendEmail")
    public ResponseEntity<?> sendmail(@RequestBody @Valid Map<String, String> Info) throws MessagingException {
        String email = Info.get("email");
        sendVerficationcode.sendMail(email);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "이메일 인증", description = "이메일로 인증을 실시합니다.")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @PostMapping("/forgot-password/email_confirm")
    public ResponseEntity<?> ConfirmCode(@RequestBody @Valid Map<String, String> UserInfo) throws MessagingException {
        String email = UserInfo.get("email");
        String Usercode = UserInfo.get("Usercode");
        sendVerficationcode.VerficationEmail(email, Usercode);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(email));
    }




}


