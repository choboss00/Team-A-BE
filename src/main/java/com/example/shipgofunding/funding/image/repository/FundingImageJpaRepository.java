package com.example.shipgofunding.funding.image.repository;

import com.example.shipgofunding.funding.image.domain.FundingImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundingImageJpaRepository extends JpaRepository<FundingImage, Integer> {

    @Query("SELECT fi FROM FundingImage fi " +
            "JOIN FETCH fi.funding f " +
            "WHERE f.id = :fundingId ")
    List<FundingImage> findByFundingId(@Param("fundingId") Integer fundingId);
}
