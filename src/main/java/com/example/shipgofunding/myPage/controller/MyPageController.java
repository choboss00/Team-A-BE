package com.example.shipgofunding.myPage.controller;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.funding.response.FundingResponse;
import com.example.shipgofunding.myPage.request.MyPageRequest.MyPageUpdateRequestDTO;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageUpdateResponseDTO;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageFundingCountsResponseDTO;
import com.example.shipgofunding.myPage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "마이페이지 참여중인 펀딩 목록 조회", description = "마이페이지 참여중인 펀딩 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "참여중인 펀딩 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyPageFundingCountsResponseDTO.class)))
    @GetMapping("/my-page/funding-counts")
    public ResponseEntity<?> getFundingCounts(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        MyPageFundingCountsResponseDTO responseDTO = myPageService.getFundingCounts(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }

    @Operation(summary = "프로필 업데이트", description = "프로필을 업데이트합니다.")
    @PatchMapping("/my-page")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid MyPageUpdateRequestDTO requestDTO, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        MyPageUpdateResponseDTO responseDTO = myPageService.updateProfile(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(responseDTO));
    }

    // TO-DO : 기획 부분이 미정인 API
    @GetMapping("/my-page/histories")
    public ResponseEntity<?> getHistories(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success("미정 API"));
    }

    @GetMapping("/my-page/participations")
    public ResponseEntity<?> getParticipations(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success("미정 API"));
    }

    @GetMapping("/my-page/likes")
    public ResponseEntity<?> getLikes(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success("미정 API"));
    }

    @GetMapping("/my-page/notifications")
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal PrincipalUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success("미정 API"));
    }

}
