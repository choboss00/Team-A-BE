package com.example.shipgofunding.funding.response;

import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FundingResponse {

    @Getter
    @Setter
    public static class BannerResponseDTO {
        private int bannerId;
        private String bannerImageUrl;

        public BannerResponseDTO(Banner banner) {
            this.bannerId = banner.getId();
            this.bannerImageUrl = banner.getImage();
        }

    }

    @Getter
    @Setter
    public static class PopularFundingMainPageResponseDTO {
        private int fundingId;
        private String fundingImage;
        private String category;
        private String fundingTitle;
        private int individualPrice;

        public PopularFundingMainPageResponseDTO(FundingImage fundingImage) {
            this.fundingId = fundingImage.getFunding().getId();
            this.fundingImage = fundingImage.getFundingImage();
            this.category = fundingImage.getFunding().getCategory();
            this.fundingTitle = fundingImage.getFunding().getFundingTitle();
            this.individualPrice = fundingImage.getFunding().getIndividualPrice();
        }
    }

    @Getter
    @Setter
    public static class UrgentFundingResponseDTO {
        private int fundingId;
        private String fundingImage;
        private String startTime;
        private String endTime;
        private String timeRemaining;

        // LocalDateTime을 String으로 포맷하기 위한 formatter 정의
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public UrgentFundingResponseDTO(FundingImage fundingImage) {
            this.fundingId = fundingImage.getFunding().getId();
            this.fundingImage = fundingImage.getFundingImage();
            this.startTime = fundingImage.getFunding().getStartDate().format(formatter);
            this.endTime = fundingImage.getFunding().getEndDate().format(formatter);
            this.timeRemaining = calculateTimeRemaining(fundingImage.getFunding().getEndDate());
        }

        // 종료 시간까지 남은 시간을 계산하는 메소드
        private String calculateTimeRemaining(LocalDateTime endDate) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, endDate);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            // 남은 시간이 없거나 과거인 경우 "00:00:00"을 반환
            if (hours < 0) {
                return "00:00:00";
            } else {
                return String.format("%02d:%02d:%02d", hours, minutes, seconds);
            }
        }
    }


}
