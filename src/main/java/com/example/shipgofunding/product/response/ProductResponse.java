package com.example.shipgofunding.product.response;

import com.example.shipgofunding.product.banner.domain.Banner;
import lombok.Getter;
import lombok.Setter;

public class ProductResponse {

    @Getter
    @Setter
    public static class BannerResponse {
        private int bannerId;
        private String bannerImageUrl;

        public BannerResponse(Banner banner) {
            this.bannerId = banner.getId();
            this.bannerImageUrl = banner.getImage();
        }

    }
}
