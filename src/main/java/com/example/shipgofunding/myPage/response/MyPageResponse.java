package com.example.shipgofunding.myPage.response;

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
}
