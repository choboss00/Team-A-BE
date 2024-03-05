package com.example.shipgofunding.funding.participatingFunding.repository;

import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.participatingFunding.domain.ParticipatingFunding;
import com.example.shipgofunding.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipatingFundingJpaRepository extends JpaRepository<ParticipatingFunding, Integer> {
    int countByFundingId(Integer id);

    boolean existsByFundingIdAndUserId(int fundingId, Long id);

    void deleteByFundingIdAndUserId(int fundingId, Long id);

    int countByUserId(Long id);
}
