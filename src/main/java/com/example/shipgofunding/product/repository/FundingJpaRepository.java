package com.example.shipgofunding.product.repository;

import com.example.shipgofunding.product.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingJpaRepository extends JpaRepository<Funding, Integer> {
}
