package com.example.shipgofunding.myPage.controller;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageFundingCountsResponseDTO;
import com.example.shipgofunding.myPage.service.MyPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "My-Page", description = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private MyPageService myPageService;

    @GetMapping("/my-page/funding-counts")
    public ResponseEntity<?> getFundingCounts(@@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        MyPageFundingCountsResponseDTO responseDTO = myPageService.getFundingCounts(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }
}
