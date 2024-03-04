package com.example.shipgofunding.funding.banner.response;

import com.example.shipgofunding.funding.banner.domain.Banner;
import lombok.Getter;
import lombok.Setter;

public class BannerResponse {

    @Getter
    @Setter
    public static class BannerResponseDTO {
        private int fundingId;
        private int bannerId;
        private String bannerImage;

        public BannerResponseDTO(Banner banner) {
            this.fundingId = banner.getFunding().getId();
            this.bannerId = banner.getId();
            this.bannerImage = banner.getImage();
        }

    }

}
