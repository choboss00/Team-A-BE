package com.example.shipgofunding.funding.banner.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BannerRequest {

    @Getter
    @Setter
    public static class BannerCreateRequestDTO {
        private String imageUrl;
        private int fundingId;
    }
}
