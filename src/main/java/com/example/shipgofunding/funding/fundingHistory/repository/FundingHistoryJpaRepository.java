package com.example.shipgofunding.funding.fundingHistory.repository;

import com.example.shipgofunding.funding.fundingHistory.domain.FundingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingHistoryJpaRepository extends JpaRepository<FundingHistory, Integer> {
}
