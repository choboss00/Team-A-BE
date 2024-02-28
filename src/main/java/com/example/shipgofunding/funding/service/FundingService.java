package com.example.shipgofunding.funding.service;

import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.banner.repository.BannerJpaRepository;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import com.example.shipgofunding.funding.image.repository.FundingImageJpaRepository;
import com.example.shipgofunding.funding.repository.FundingJpaRepository;
import com.example.shipgofunding.funding.response.FundingResponse.UrgentFundingResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.BannerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class FundingService {

    private BannerJpaRepository bannerJpaRepository;
    private FundingJpaRepository fundingJpaRepository;
    private FundingImageJpaRepository fundingImageJpaRepository;

    @Autowired
    public FundingService(BannerJpaRepository bannerJpaRepository, FundingJpaRepository fundingJpaRepository, FundingImageJpaRepository fundingImageJpaRepository) {
        this.bannerJpaRepository = bannerJpaRepository;
        this.fundingJpaRepository = fundingJpaRepository;
        this.fundingImageJpaRepository = fundingImageJpaRepository;
    }

    public List<BannerResponseDTO> getMainBanners() {
        List<Banner> banners = bannerJpaRepository.findRandomBanners();

        return banners.stream()
                .map(BannerResponseDTO::new)
                .toList();
    }

    public List<UrgentFundingResponseDTO> getUrgentFundingImages() {
        // TO-DO : 72시간 이내 랜덤한 펀딩 상품을 3개 조회해서 가져오기
        List<Funding> fundings = fundingJpaRepository.findRandomFundings();

        List<UrgentFundingResponseDTO> urgentFundingImages = new ArrayList<>();

        for ( Funding funding : fundings ) {
            FundingImage fundingImage = findFirstByFundingId(funding.getId());

            // 사진 추가하기
            urgentFundingImages.add(new UrgentFundingResponseDTO(fundingImage));
        }

        return urgentFundingImages;
    }
    
    // 1장의 이미지 데이터만을 가져오기 위한 메소드
    public FundingImage findFirstByFundingId(Integer fundingId) {
        Page<FundingImage> page = fundingImageJpaRepository.findByFundingId(fundingId, PageRequest.of(0, 1));
        return page.stream().findFirst().orElse(null);
    }
}
