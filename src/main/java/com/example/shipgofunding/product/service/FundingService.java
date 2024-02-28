package com.example.shipgofunding.product.service;

import com.example.shipgofunding.product.banner.domain.Banner;
import com.example.shipgofunding.product.banner.repository.BannerJpaRepository;
import com.example.shipgofunding.product.response.FundingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class FundingService {

    private BannerJpaRepository bannerJpaRepository;

    @Autowired
    public FundingService(BannerJpaRepository bannerJpaRepository) {
        this.bannerJpaRepository = bannerJpaRepository;
    }

    public List<FundingResponse.BannerResponseDTO> getMainBanners() {
        List<Banner> banners = bannerJpaRepository.findRandomBanners();

        return banners.stream()
                .map(FundingResponse.BannerResponseDTO::new)
                .toList();
    }
}
