package com.example.shipgofunding.funding.participatingFunding.repository;

import com.example.shipgofunding.funding.participatingFunding.domain.ParticipatingFunding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatingFundingJpaRepository extends JpaRepository<ParticipatingFunding, Integer> {
    int countByFundingId(Integer id);
}
