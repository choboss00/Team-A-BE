package com.example.shipgofunding.funding.image.repository;

import com.example.shipgofunding.funding.image.domain.FundingImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingImageJpaRepository extends JpaRepository<FundingImage, Integer> {
}
