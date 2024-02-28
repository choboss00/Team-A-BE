package com.example.shipgofunding.product.controller;

import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.product.response.FundingResponse;
import com.example.shipgofunding.product.service.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FundingController {

    private final FundingService fundingService;

    @Operation(summary = "메인 배너 조회", description = "메인 페이지에 표시될 배너 데이터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 배너 데이터 조회",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FundingResponse.BannerResponseDTO.class)))
    @GetMapping("/banners/main")
    public ResponseEntity<?> getMainBanners() {
        //TO-DO : 메인 배너 데이터를 조회하는 로직 구현하기
        List<FundingResponse.BannerResponseDTO> banners = fundingService.getMainBanners();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(banners));
    }

}
