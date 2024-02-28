package com.example.shipgofunding.funding.image.repository;

import com.example.shipgofunding.funding.image.domain.FundingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundingImageJpaRepository extends JpaRepository<FundingImage, Integer> {

    @Query("SELECT fi FROM FundingImage fi WHERE fi.funding.id = :id LIMIT 1")
    FundingImage findFirstImageByFundingId(Integer id);
}
