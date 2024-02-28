package com.example.shipgofunding.funding.response;

import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.domain.Funding;
import lombok.Getter;
import lombok.Setter;

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

}
