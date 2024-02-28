package com.example.shipgofunding.product.response;

import com.example.shipgofunding.product.banner.domain.Banner;
import lombok.Getter;
import lombok.Setter;

public class ProductResponse {

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
