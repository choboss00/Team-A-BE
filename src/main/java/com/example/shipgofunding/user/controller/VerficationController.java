package com.example.shipgofunding.user.controller;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.user.request.UserRequest;
import com.example.shipgofunding.user.service.SendVerficationcode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class VerficationController {

    @Autowired
    private SendVerficationcode sendVerficationcode;



    @Operation(summary = "이메일 인증 코드", description = "이메일로 인증 코드를 보냅니다.")
    @ApiResponse(responseCode = "200", description = "인증코드 전송 성공")
    @PostMapping("/send")
    public ResponseEntity<?> sendmail(@RequestBody @Valid UserRequest.SignupRequestDTO sendDTO) throws MessagingException {
        String email = sendDTO.getEmail();
        sendVerficationcode.sendMail(email);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }



}


