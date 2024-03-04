package com.example.shipgofunding.funding.service;

import com.example.shipgofunding.comment.domain.Comment;
import com.example.shipgofunding.comment.repository.CommentJpaRepository;
import com.example.shipgofunding.comment.response.CommentResponse.CommentResponseDTO;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.config.errors.exception.Exception401;
import com.example.shipgofunding.config.errors.exception.Exception404;
import com.example.shipgofunding.funding.banner.domain.Banner;
import com.example.shipgofunding.funding.banner.repository.BannerJpaRepository;
import com.example.shipgofunding.funding.banner.response.BannerResponse.BannerResponseDTO;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.fundingHeart.repository.FundingHeartJpaRepository;
import com.example.shipgofunding.funding.image.domain.FundingImage;
import com.example.shipgofunding.funding.image.repository.FundingImageJpaRepository;
import com.example.shipgofunding.funding.image.response.FundingImageResponse.FundingImageResponseDTO;
import com.example.shipgofunding.funding.participatingFunding.repository.ParticipatingFundingJpaRepository;
import com.example.shipgofunding.funding.repository.FundingJpaRepository;
import com.example.shipgofunding.funding.request.FundingRequest;
import com.example.shipgofunding.funding.request.FundingRequest.CreateFundingRequestDTO;
import com.example.shipgofunding.funding.response.FundingResponse.FundingDetailResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.FundingResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.PopularFundingMainPageResponseDTO;
import com.example.shipgofunding.funding.response.FundingResponse.UrgentFundingResponseDTO;
import com.example.shipgofunding.user.domain.User;
import com.example.shipgofunding.user.repository.UserRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class FundingService {

    private BannerJpaRepository bannerJpaRepository;
    private FundingJpaRepository fundingJpaRepository;
    private FundingImageJpaRepository fundingImageJpaRepository;
    private FundingHeartJpaRepository fundingHeartJpaRepository;
    private ParticipatingFundingJpaRepository participatingFundingJpaRepository;
    private CommentJpaRepository commentJpaRepository;
    private UserRepository userRepository;

    @Autowired
    public FundingService(BannerJpaRepository bannerJpaRepository, FundingJpaRepository fundingJpaRepository, FundingImageJpaRepository fundingImageJpaRepository, FundingHeartJpaRepository fundingHeartJpaRepository, ParticipatingFundingJpaRepository participatingFundingJpaRepository, CommentJpaRepository commentJpaRepository, UserRepository userRepository) {
        this.bannerJpaRepository = bannerJpaRepository;
        this.fundingJpaRepository = fundingJpaRepository;
        this.fundingImageJpaRepository = fundingImageJpaRepository;
        this.fundingHeartJpaRepository = fundingHeartJpaRepository;
        this.participatingFundingJpaRepository = participatingFundingJpaRepository;
        this.commentJpaRepository = commentJpaRepository;
        this.userRepository = userRepository;
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

            int count = participatingFundingJpaRepository.countByFundingId(funding.getId());

            fundingResponseDTOs.add(new FundingResponseDTO(fundingImage, count));
        }

        return fundingResponseDTOs;

    }

    /**
     * 1. 펀딩 상품 목록 조회
     * 2. 펀딩 상품의 이미지 목록 조회
     * 3. 펀딩 상품의 댓글 목록 조회
     * **/
    public FundingDetailResponseDTO getFundingDetail(int fundingId) {
        Funding funding = fundingJpaRepository.findById(fundingId)
                .orElseThrow(() -> new Exception404("해당 펀딩 상품이 존재하지 않습니다."));

        List<FundingImage> fundingImages = fundingImageJpaRepository.findAllByFundingId(fundingId);

        List<FundingImageResponseDTO> fundingImageResponses = fundingImages.stream()
                .map(FundingImageResponseDTO::new)
                .collect(Collectors.toList());

        List<Comment> comments = commentJpaRepository.findAllByFundingId(fundingId);

        List<CommentResponseDTO> commentResponses = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());

        return new FundingDetailResponseDTO(funding, fundingImageResponses, commentResponses);
    }

    public User validateUser(PrincipalUserDetails userDetails) {
        // 사용자 인증 로직 구현
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new Exception404("유저 정보를 찾을 수 없습니다."));
    }
    @Transactional
    public int saveFunding(CreateFundingRequestDTO requestDTO, PrincipalUserDetails userDetails) {
        /**
         * 1. user 인증 과정
         * 2. 펀딩 상품 저장
         * 3. 펀딩 이미지 저장
         * **/
        // 올바른 이미지 확장자를 넣었는지 인증하는 로직
        if (!requestDTO.isValidImageUrls()) {
            throw new Exception400(null, "하나 이상의 이미지 URL이 유효하지 않은 형식입니다.");
        }

        User user = validateUser(userDetails);

        Funding funding = Funding.builder()
                .user(user)
                .fundingTitle(requestDTO.getFundingTitle())
                .fundingSummary(requestDTO.getFundingSummary())
                .fundingDescription(requestDTO.getFundingDescription())
                .category(requestDTO.getCategory())
                .individualPrice(requestDTO.getIndividualPrice())
                .totalPrice(requestDTO.getTotalPrice())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .build();
        // 펀딩 상품 저장
        fundingJpaRepository.save(funding);

        // 펀딩 이미지 저장
        for (String imageUrl : requestDTO.getImageUrls()) {
            FundingImage fundingImage = FundingImage.builder()
                    .funding(funding)
                    .fundingImage(imageUrl)
                    .build();
            fundingImageJpaRepository.save(fundingImage);
        }

        return funding.getId();

    }

    @Transactional
    public void updateFunding(int fundingId, FundingRequest.UpdateFundingRequestDTO requestDTO, PrincipalUserDetails userDetails) {
        /**
         * 펀딩 상세 페이지 수정하는 로직 작성하기
         * 1. user 검증
         * 2. funding 상품 검증
         * 3. funding 상품 수정하기
         * 4. funding 이미지 수정하기
         * */

        // user 검증
        User user = validateUser(userDetails);

        // funding 상품 검증
        Funding funding = fundingJpaRepository.findById(fundingId)
                .orElseThrow(() -> new Exception404("해당 펀딩 상품이 존재하지 않습니다."));

        // user와 funding의 user가 같은지 확인
        if ( !funding.getUser().getId().equals(user.getId()) ) {
            throw new Exception401("해당 펀딩 상품을 수정할 권한이 없습니다.");
        }

        // funding 상품 수정하기
        funding.updateFunding(requestDTO);

        // funding 이미지 수정하기
        List<FundingImage> fundingImages = fundingImageJpaRepository.findAllByFundingId(fundingId);

        fundingImageJpaRepository.deleteAll(fundingImages);

        for (String imageUrl : requestDTO.getImageUrls()) {
            FundingImage fundingImage = FundingImage.builder()
                    .funding(funding)
                    .fundingImage(imageUrl)
                    .build();
            fundingImageJpaRepository.save(fundingImage);
        }

    }

    public void deleteFunding(int fundingId, PrincipalUserDetails userDetails) {
        /**
         * 1. user 인증
         * 2. funding 상품 검증
         * 3. funding 상품 삭제
         * 4. funding 상품 이미지 삭제
         * **/

        // user 인증
        User user = validateUser(userDetails);

        // funding 상품 검증
        Funding funding = fundingJpaRepository.findById(fundingId)
                .orElseThrow(() -> new Exception404("해당 펀딩 상품이 존재하지 않습니다."));

        // user와 funding의 user가 같은지 확인
        if ( !funding.getUser().getId().equals(user.getId()) ) {
            throw new Exception401("해당 펀딩 상품을 삭제할 권한이 없습니다.");
        }

        // funding 상품 삭제
        fundingJpaRepository.delete(funding);

        // funding 상품 이미지 삭제
        List<FundingImage> fundingImages = fundingImageJpaRepository.findAllByFundingId(fundingId);

        fundingImageJpaRepository.deleteAll(fundingImages);

    }
}
