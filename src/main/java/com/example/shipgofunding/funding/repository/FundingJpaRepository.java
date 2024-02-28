package com.example.shipgofunding.funding.repository;

import com.example.shipgofunding.funding.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingJpaRepository extends JpaRepository<Funding, Integer> {
}
