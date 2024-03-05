package com.example.shipgofunding.myPage.service;

import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.funding.fundingHeart.repository.FundingHeartJpaRepository;
import com.example.shipgofunding.funding.notificationFunding.repository.NotificationJpaRepository;
import com.example.shipgofunding.funding.participatingFunding.repository.ParticipatingFundingJpaRepository;
import com.example.shipgofunding.myPage.request.MyPageRequest.MyPageUpdateRequestDTO;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageUpdateResponseDTO;
import com.example.shipgofunding.myPage.response.MyPageResponse.MyPageFundingCountsResponseDTO;
import com.example.shipgofunding.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final ParticipatingFundingJpaRepository participatingFundingJpaRepository;
    private final FundingHeartJpaRepository fundingHeartJpaRepository;
    private final NotificationJpaRepository notificationFundingJpaRepository;

    public MyPageFundingCountsResponseDTO getFundingCounts(PrincipalUserDetails userDetails) {
        /**
         * 참여중인 펀딩, 관심있는 펀딩, 알림신청한 펀딩 개수 목록 가져오기
         * 1. 참여중인 펀딩 목록 가져오기
         * 2. 관심있는 펀딩 목록 가져오기
         * 3. 알림신청한 펀딩 목록 가져오기
         * **/

        User user = userDetails.getUser();

        int participationFundingCounts = participatingFundingJpaRepository.countByUserId(user.getId());
        int likeFundingCounts = fundingHeartJpaRepository.countByUserId(user.getId());
        int notificationFundingCounts = notificationFundingJpaRepository.countByUserId(user.getId());

        return new MyPageFundingCountsResponseDTO(participationFundingCounts, likeFundingCounts, notificationFundingCounts);
    }

    @Transactional
    public MyPageUpdateResponseDTO updateProfile(MyPageUpdateRequestDTO requestDTO, PrincipalUserDetails userDetails) {
        User user = userDetails.getUser();
        user.updateProfile(requestDTO);

        return new MyPageUpdateResponseDTO(user.getImage(), user.getNickname());
    }


}
