package com.example.shipgofunding.funding.controller;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.config.errors.exception.Exception401;
import com.example.shipgofunding.config.s3.S3UploadService;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.funding.banner.request.BannerRequest.BannerCreateRequestDTO;
import com.example.shipgofunding.funding.banner.response.BannerResponse.BannerResponseDTO;
import com.example.shipgofunding.funding.request.FundingRequest.UpdateFundingRequestDTO;
import com.example.shipgofunding.funding.request.FundingRequest.CreateFundingRequestDTO;
import com.example.shipgofunding.funding.response.FundingResponse.FundingDetailResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.FundingResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.PopularFundingMainPageResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.UrgentFundingResponseDTO;
import com.example.shipgofunding.funding.service.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FundingController {

    private final FundingService fundingService;
    private final S3UploadService s3UploadService;

    @GetMapping("/fundings/update-status")
    public ResponseEntity<?> updateFundingStatus() {
        fundingService.updateFundingStatus();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "메인 배너 조회", description = "메인 페이지에 표시될 배너 데이터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 배너 데이터 조회",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BannerResponseDTO.class)))
    @GetMapping("/banners/main")
    public ResponseEntity<?> getMainBanners() {
        //TO-DO : 메인 배너 데이터를 조회하는 로직 구현하기
        List<BannerResponseDTO> banners = fundingService.getMainBanners();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(banners));
    }

    @Operation(summary = "72 시간 이내 상품 3개 조회", description = "메인 페이지에 표시될 마감임박 상품 데이터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 마감임박 상품 데이터 조회",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = UrgentFundingResponseDTO.class)))
    @GetMapping("/fundings/urgent")
    public ResponseEntity<?> getUrgentFundings() {
        //TO-DO : 긴급 펀딩 데이터를 조회하는 로직 구현하기 ( 3개의 랜덤 마감 임박 데이터를 뽑아내야함 )
        List<UrgentFundingResponseDTO> urgentFundingImages = fundingService.getUrgentFundingImages();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(urgentFundingImages));
    }

    @Operation(summary = "인기 상품 조회", description = "메인 페이지에 표시될 인기 상품 데이터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 인기 상품 데이터 조회",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = PopularFundingMainPageResponseDTO.class)))
    @GetMapping(value = "/fundings/popular", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getPopularMainPageFundings() {
        //TO-DO : 인기 펀딩 데이터를 조회하는 로직 구현하기 ( 인기순으로 정렬된 6개의 데이터를 뽑아내야 함 )
        List<PopularFundingMainPageResponseDTO> popularFundings = fundingService.getPopularMainPageFundings();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(popularFundings));
    }


    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 목록 조회",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = FundingResponseDTO.class)))
    @GetMapping("/fundings")
    public ResponseEntity<?> getFundings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer min_price,
            @RequestParam(required = false) Integer max_price,
            @RequestParam(required = false) String sorted,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        // Pageable 인스턴스 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        // 서비스 메서드 호출
        List<FundingResponseDTO> fundings = fundingService.getFundings(category, search, min_price, max_price, sorted, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundings));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 상세 정보 조회",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = FundingDetailResponseDTO.class)))
    @GetMapping("/fundings/{fundingId}")
    public ResponseEntity<?> getFundingDetail(@PathVariable int fundingId) {
        //TO-DO : 펀딩 상세 정보를 조회하는 로직 구현하기
        FundingDetailResponseDTO fundingDetail = fundingService.getFundingDetail(fundingId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundingDetail));
    }

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 등록")
    @PostMapping("/fundings")
    public ResponseEntity<?> createFunding(@RequestBody @Valid CreateFundingRequestDTO requestDTO, Errors errors, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        int fundingId = fundingService.saveFunding(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundingId));
    }

    @Operation(summary = "상품 이미지 업로드", description = "상품 이미지를 업로드합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 이미지 업로드")
    @PostMapping(value = "/fundings/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@RequestPart @Valid List<MultipartFile> images, @AuthenticationPrincipal PrincipalUserDetails userDetails) {

        if ( fundingService.validateUser(userDetails) == null ) {
            throw new Exception401("이미지를 업로드할 권한이 없습니다.");
        }

        try {
            List<String> uploadedImageUrls = s3UploadService.uploadMultipleFiles(images);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(uploadedImageUrls));
        } catch (IOException e) {
            throw new Exception400(null, "이미지 업로드에 실패했습니다.");
        }
    }

    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 수정",
    content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = UpdateFundingRequestDTO.class)))
    @PutMapping("/fundings/{fundingId}")
    public ResponseEntity<?> updateFunding(@PathVariable int fundingId, @RequestBody @Valid UpdateFundingRequestDTO requestDTO, Errors errors, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.updateFunding(fundingId, requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundingId));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품 삭제")
    @DeleteMapping("/fundings/{fundingId}")
    public ResponseEntity<?> deleteFunding(@PathVariable int fundingId, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.deleteFunding(fundingId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "펀딩 상품 신청하기", description = "펀딩 상품에 신청합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 펀딩 상품 신청하기")
    @PostMapping("/fundings/{fundingId}/apply")
    public ResponseEntity<?> applyFunding(@PathVariable int fundingId, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.applyFunding(fundingId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundingId));
    }

    @Operation(summary = "펀딩 상품 신청 취소하기", description = "펀딩 상품에 신청을 취소합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 펀딩 상품 신청 취소하기")
    @DeleteMapping("/fundings/{fundingId}/apply")
    public ResponseEntity<?> cancelApplyFunding(@PathVariable int fundingId, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.cancelApplyFunding(fundingId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "펀딩 상품 좋아요 버튼", description = "펀딩 상품에 좋아요를 누릅니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 펀딩 상품 좋아요 버튼이 눌림")
    @PostMapping("/fundings/{fundingId}/likes")
    public ResponseEntity<?> likesFunding(@PathVariable int fundingId, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.likesFunding(fundingId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(fundingId));
    }

    @Operation(summary = "펀딩 상품 좋아요 취소하기", description = "펀딩 상품에 좋아요를 취소합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 펀딩 상품 좋아요 취소하기")
    @DeleteMapping("/fundings/{fundingId}/likes")
    public ResponseEntity<?> cancelLikesFunding(@PathVariable int fundingId, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.cancelLikesFunding(fundingId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @PostMapping("/banners")
    public ResponseEntity<?> createBanner(@RequestBody @Valid BannerCreateRequestDTO requestDTO, Errors errors, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        fundingService.saveBanner(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

}
