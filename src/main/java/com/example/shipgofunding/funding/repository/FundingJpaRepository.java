package com.example.shipgofunding.funding.repository;

import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.fundingHeart.domain.FundingHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundingJpaRepository extends JpaRepository<Funding, Integer>, JpaSpecificationExecutor<Funding> {
    @Query(value = "SELECT f.id FROM fundings f " +
            "WHERE f.end_date >= CURRENT_TIMESTAMP AND " +
            "f.end_date < CURRENT_TIMESTAMP + INTERVAL '72' HOUR " +
            "ORDER BY RAND() " +
            "LIMIT 3", nativeQuery = true)
    List<Integer> findRandomFundings();

    @Query("SELECT f FROM Funding f " +
            "WHERE f.endDate >= CURRENT_TIMESTAMP AND f.startDate < CURRENT_TIMESTAMP " +
            "ORDER BY f.likesCount DESC " +
            "LIMIT 6")
    List<Funding> findPopularFundings();
}
