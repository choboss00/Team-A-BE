package com.example.shipgofunding.funding.image.response;

import com.example.shipgofunding.funding.image.domain.FundingImage;
import lombok.Getter;
import lombok.Setter;

public class FundingImageResponse {

    @Getter
    @Setter
    public static class FundingImageResponseDTO {
        private int fundingImageId;
        private String fundingImage;

        public FundingImageResponseDTO (FundingImage fundingImage) {
            this.fundingImageId = fundingImage.getId();
            this.fundingImage = fundingImage.getFundingImage();
        }
    }

}
