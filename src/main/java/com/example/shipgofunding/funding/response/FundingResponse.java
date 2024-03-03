package com.example.shipgofunding.funding.response;

import com.example.shipgofunding.comment.domain.Comment;
import com.example.shipgofunding.comment.response.CommentResponse.CommentResponseDTO;
import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.domain.FundingEnum;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import com.example.shipgofunding.funding.image.response.FundingImageResponse.FundingImageResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FundingResponse {

    @Getter
    @Setter
    public static class FundingResponseDTO {
        private int fundingId;
        private String fundingTitle;
        private String fundingImage;
        private int individualPrice;
        private double fundingPercent;
        private String nickname;
        private int likes;

        // likes 의 경우 직접 DB 에서 조회 후 값을 넣어줄 예정
        public FundingResponseDTO(FundingImage fundingImage, int count) {
            this.fundingId = fundingImage.getFunding().getId();
            this.fundingTitle = fundingImage.getFunding().getFundingTitle();
            this.fundingImage = fundingImage.getFundingImage();
            this.individualPrice = fundingImage.getFunding().getIndividualPrice();
            this.fundingPercent = calculateFundingPercent(fundingImage.getFunding()) * count;
            this.nickname = fundingImage.getFunding().getUser().getNickname();
            this.likes = fundingImage.getFunding().getLikesCount();
        }

        private double calculateFundingPercent(Funding funding) {
            return (double) funding.getIndividualPrice() / funding.getTotalPrice() * 100;
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
        private String nickname;

        public PopularFundingMainPageResponseDTO(FundingImage fundingImage) {
            this.fundingId = fundingImage.getFunding().getId();
            this.fundingImage = fundingImage.getFundingImage();
            this.category = fundingImage.getFunding().getCategory();
            this.fundingTitle = fundingImage.getFunding().getFundingTitle();
            this.individualPrice = fundingImage.getFunding().getIndividualPrice();
            this.nickname = fundingImage.getFunding().getUser().getNickname();
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
        public String calculateTimeRemaining(LocalDateTime endDate) {
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

    @Getter
    @Setter
    public static class FundingDetailResponseDTO {
        private int fundingId;
        private String category;
        private FundingEnum state;
        private String fundingTitle;
        private String fundingSummary;
        private double fundingPercent;
        private int individualPrice;
        private List<FundingImageResponseDTO> fundingImages;
        private String fundingDescription;
        private List<CommentResponseDTO> comments;

        public FundingDetailResponseDTO(Funding funding, List<FundingImageResponseDTO> fundingImages, List<CommentResponseDTO> comments) {
            this.fundingId = funding.getId();
            this.category = funding.getCategory();
            this.state = funding.getFundingEnum();
            this.fundingTitle = funding.getFundingTitle();
            this.fundingSummary = funding.getFundingSummary();
            this.fundingPercent = calculateFundingPercent(funding);
            this.individualPrice = funding.getIndividualPrice();
            this.fundingImages = fundingImages;
            this.fundingDescription = funding.getFundingDescription();
            this.comments = comments;
        }

        private double calculateFundingPercent(Funding funding) {
            return (double) funding.getIndividualPrice() / funding.getTotalPrice() * 100;
        }

    }


}
