package com.example.shipgofunding.user.controller;

import com.example.shipgofunding.config.Redis.RedisUtils;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest.SendEmailRequestDTO;
import com.example.shipgofunding.user.request.UserRequest.VerficationRequestDTO;
import com.example.shipgofunding.user.response.UserResponse;
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

@Tag(name = "User", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VerficationController {

    @Autowired
    private SendVerficationcode sendVerficationcode;
    private UserRepository userRepository;
    private final RedisUtils redisUtils;

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일로 인증 코드를 보냅니다.")
    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공")
    @PostMapping("/forgot-password/send-email")
    public ResponseEntity<?> sendmail(@RequestBody @Valid SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
        sendVerficationcode.sendMail(sendEmailRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "이메일 인증", description = "이메일로 인증을 실시합니다.")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @PostMapping("/forgot-password/email-confirm")
    public ResponseEntity<?> ConfirmCode(@RequestBody @Valid VerficationRequestDTO verficationRequestDTO)  {
        sendVerficationcode.VerficationEmail(verficationRequestDTO);
        UserResponse.VerficationResponseDTO verficationResponseDTO=new UserResponse.VerficationResponseDTO(verficationRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(verficationResponseDTO.getEmail()));
    }

    @Operation(summary = "회원가입 이메일 인증 코드 전송", description = "이메일로 인증 코드를 보냅니다.")
    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공")
    @PostMapping("/send-email")
    public ResponseEntity<?> signupsendemail(@RequestBody @Valid SendEmailRequestDTO sendEmailRequestDTO)  throws MessagingException {
        sendVerficationcode.signupsendMail(sendEmailRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "회원가입 이메일 인증", description = "이메일로 인증을 실시합니다.")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @PostMapping("/email-confirm")
    public ResponseEntity<?> signupConfirmCode(@RequestBody @Valid VerficationRequestDTO verficationRequestDTO)  {
        sendVerficationcode.VerficationEmail(verficationRequestDTO);
        UserResponse.VerficationResponseDTO verficationResponseDTO=new UserResponse.VerficationResponseDTO(verficationRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(verficationResponseDTO.getEmail()));
    }



}


