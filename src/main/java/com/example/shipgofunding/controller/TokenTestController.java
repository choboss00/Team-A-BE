package com.example.shipgofunding.controller;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenTestController {
    
    // 유저 정보를 잘 가져오는지 확인하는 컨트롤러
    @GetMapping("/test/token-test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> tokenTest(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(userDetails.getUser()));
    }
}
