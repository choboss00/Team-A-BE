package com.example.shipgofunding.funding.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class FundingRequest {

    @Getter
    @Setter
    public static class createFundingRequestDTO {

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
        private List<String> imageUrls;

    }
}
