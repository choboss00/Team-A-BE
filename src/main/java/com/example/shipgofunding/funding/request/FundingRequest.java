package com.example.shipgofunding.funding.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class FundingRequest {

    @Getter
    @Setter
    public static class CreateFundingRequestDTO {

        @NotNull(message = "카테고리는 필수 입력 값입니다.")
        private String category;

        @NotNull(message = "펀딩 시작일은 필수 입력 값입니다.")
        private LocalDateTime startDate;

        @NotNull(message = "펀딩 마감일은 필수 입력 값입니다.")
        private LocalDateTime endDate;

        @NotNull(message = "목표 금액은 필수 입력 값입니다.")
        private int totalPrice;

        @NotNull(message = "개인 펀딩 금액은 필수 입력 값입니다.")
        private int individualPrice;

        @NotNull(message = "펀딩 제목은 필수 입력 값입니다.")
        @Size(max = 20, message = "20자 이내로 입력해주세요.")
        private String fundingTitle;

        @NotNull
        @Size(max = 300, message = "300자 이내로 입력해주세요.")
        private String fundingSummary;

        @NotNull(message = "펀딩 상세 설명은 필수 입력 값입니다.")
        private String fundingDescription;

        @NotNull(message = "이미지 URL 리스트는 필수 입력 값입니다.")
        @Size(min = 1, message = "이미지 URL은 최소 1개 이상 입력해주세요.")
        private List<String> imageUrls;

        // 이미지 URL 확장자 검증 로직
        public boolean isValidImageUrls() {
            Pattern imagePattern = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");
            return imageUrls.stream().allMatch(url -> imagePattern.matcher(url).matches());
        }

    }

    @Getter
    @Setter
    public static class UpdateFundingRequestDTO {
        @NotNull(message = "카테고리는 필수 입력 값입니다.")
        private String category;

        @NotNull(message = "펀딩 시작일은 필수 입력 값입니다.")
        private LocalDateTime startDate;

        @NotNull(message = "펀딩 마감일은 필수 입력 값입니다.")
        private LocalDateTime endDate;

        @NotNull(message = "목표 금액은 필수 입력 값입니다.")
        private int totalPrice;

        @NotNull(message = "개인 펀딩 금액은 필수 입력 값입니다.")
        private int individualPrice;

        @NotNull(message = "펀딩 제목은 필수 입력 값입니다.")
        @Size(max = 20, message = "20자 이내로 입력해주세요.")
        private String fundingTitle;

        @NotNull
        @Size(max = 300, message = "300자 이내로 입력해주세요.")
        private String fundingSummary;

        @NotNull(message = "펀딩 상세 설명은 필수 입력 값입니다.")
        private String fundingDescription;

        @NotNull(message = "이미지 URL 리스트는 필수 입력 값입니다.")
        @Size(min = 1, message = "이미지 URL은 최소 1개 이상 입력해주세요.")
        private List<String> imageUrls;

        // 이미지 URL 확장자 검증 로직
        public boolean isValidImageUrls() {
            Pattern imagePattern = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)");
            return imageUrls.stream().allMatch(url -> imagePattern.matcher(url).matches());
        }
    }
}
