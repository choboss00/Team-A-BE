package com.example.shipgofunding.myPage.response;

import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import lombok.Getter;
import lombok.Setter;

public class MyPageResponse {

    @Getter
    @Setter
    public static class MyPageFundingCountsResponseDTO {
        private int participationFundingCounts;
        private int likeFundingCounts;
        private int notificationFundingCounts;

        public MyPageFundingCountsResponseDTO(int participationFundingCounts, int likeFundingCounts, int notificationFundingCounts) {
            this.participationFundingCounts = participationFundingCounts;
            this.likeFundingCounts = likeFundingCounts;
            this.notificationFundingCounts = notificationFundingCounts;
        }
    }

    @Getter
    @Setter
    public static class MyPageUpdateResponseDTO {
        private int userId;
        private String imageUrl;
        private String nickname;

        public MyPageUpdateResponseDTO(String imageUrl, String nickname) {
            this.imageUrl = imageUrl;
            this.nickname = nickname;
        }
    }
}
