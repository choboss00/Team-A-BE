package com.example.shipgofunding.funding.image.repository;

import com.example.shipgofunding.funding.image.domain.FundingImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingImageJpaRepository extends JpaRepository<FundingImage, Integer> {
    Page<FundingImage> findByFundingId(Integer fundingId, Pageable pageable);
}
