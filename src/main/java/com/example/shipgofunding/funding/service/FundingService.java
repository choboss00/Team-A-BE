package com.example.shipgofunding.funding.service;

import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.banner.repository.BannerJpaRepository;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.fundingHeart.repository.FundingHeartJpaRepository;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import com.example.shipgofunding.funding.image.repository.FundingImageJpaRepository;
import com.example.shipgofunding.funding.repository.FundingJpaRepository;
import com.example.shipgofunding.funding.response.FundingResponse.FundingResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.PopularFundingMainPageResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.UrgentFundingResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.BannerResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class FundingService {

    private BannerJpaRepository bannerJpaRepository;
    private FundingJpaRepository fundingJpaRepository;
    private FundingImageJpaRepository fundingImageJpaRepository;
    private FundingHeartJpaRepository fundingHeartJpaRepository;

    @Autowired
    public FundingService(BannerJpaRepository bannerJpaRepository, FundingJpaRepository fundingJpaRepository, FundingImageJpaRepository fundingImageJpaRepository, FundingHeartJpaRepository fundingHeartJpaRepository) {
        this.bannerJpaRepository = bannerJpaRepository;
        this.fundingJpaRepository = fundingJpaRepository;
        this.fundingImageJpaRepository = fundingImageJpaRepository;
        this.fundingHeartJpaRepository = fundingHeartJpaRepository;
    }

    public List<BannerResponseDTO> getMainBanners() {
        List<Banner> banners = bannerJpaRepository.findRandomBanners();

        return banners.stream()
                .map(BannerResponseDTO::new)
                .toList();
    }

    public List<UrgentFundingResponseDTO> getUrgentFundingImages() {
        // TO-DO : 72시간 이내 랜덤한 펀딩 상품을 3개 조회해서 가져오기
        List<Integer> fundingIds = fundingJpaRepository.findRandomFundings();

        List<UrgentFundingResponseDTO> urgentFundingImages = new ArrayList<>();

        for ( Integer id : fundingIds ) {
            FundingImage fundingImage = findFirstByFundingId(id);

            // 사진 추가하기
            urgentFundingImages.add(new UrgentFundingResponseDTO(fundingImage));
        }

        return urgentFundingImages;
    }
    
    // 1장의 이미지 데이터만을 가져오기 위한 메소드
    public FundingImage findFirstByFundingId(Integer fundingId) {
        List<FundingImage> page = fundingImageJpaRepository.findByFundingId(fundingId);
        return page.stream().findFirst().orElse(null);
    }

    public List<PopularFundingMainPageResponseDTO> getPopularMainPageFundings() {
        // TO-DO : 인기 펀딩 데이터를 조회하는 로직 구현하기 ( 인기순으로 정렬된 6개의 데이터를 뽑아내야 함 )
        List<PopularFundingMainPageResponseDTO> popularFundings = new ArrayList<>();

        List<Integer> popularFundingId = fundingHeartJpaRepository.findPopularFundingHearts();


        for ( Integer id : popularFundingId ) {
            FundingImage fundingImage = findFirstByFundingId(id);

            popularFundings.add(new PopularFundingMainPageResponseDTO(fundingImage));

        }

        return popularFundings;

    }

    public List<FundingResponseDTO> getFundings(String category, String search, Integer minPrice, Integer maxPrice, String sorted, Pageable pageable) {
        // 검색 조건, 카테고리, 가격 범위, 정렬 방식을 적용한 Specification 구성하기
        Specification<Funding> spec = Specification.where(null);

        if (category != null && !category.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category));
        }

        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("fundingTitle")), "%" + search.toLowerCase() + "%"));
        }

        if ( minPrice != null ) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("individualPrice"), minPrice));
        }

        if ( maxPrice != null ) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("individualPrice"), maxPrice));
        }

        Sort sort = Sort.unsorted();

        if (sorted != null && !sorted.trim().isEmpty()) {
            switch (sorted) {
                case "인기순":
                    sort = Sort.by(Sort.Direction.DESC, "likesCount"); // @Formula로 계산된 likesCount 기준 내림차순 정렬
                    break;
                case "최신순":
                    sort = Sort.by(Sort.Direction.DESC, "createdAt");
                    break;
                case "마감 임박순":
                    sort = Sort.by(Sort.Direction.ASC, "endDate");
                    break;
            }
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Funding> fundings = fundingJpaRepository.findAll(spec, pageable);

        List<FundingResponseDTO> fundingResponseDTOs = new ArrayList<>();
        for (Funding funding : fundings) {
            FundingImage fundingImage = findFirstByFundingId(funding.getId());

            fundingResponseDTOs.add(new FundingResponseDTO(fundingImage));
        }

        return fundingResponseDTOs;

    }

}
