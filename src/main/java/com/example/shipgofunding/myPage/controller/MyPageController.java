package com.example.shipgofunding.myPage.controller;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.myPage.request.MyPageRequest.MyPageUpdateRequestDTO;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageFundingCountsResponseDTO;
import com.example.shipgofunding.myPage.service.MyPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "My-Page", description = "마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/my-page/funding-counts")
    public ResponseEntity<?> getFundingCounts(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        MyPageFundingCountsResponseDTO responseDTO = myPageService.getFundingCounts(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }

    @PatchMapping("/my-page")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid MyPageUpdateRequestDTO requestDTO, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        myPageService.updateProfile(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    // TO-DO : 기획 부분이 미정인 API
    @GetMapping("/my-page/histories")
    public ResponseEntity<?> getHistories(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success("미정 API"));
    }


}
