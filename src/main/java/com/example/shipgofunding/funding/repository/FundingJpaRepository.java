package com.example.shipgofunding.funding.repository;

import com.example.shipgofunding.funding.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundingJpaRepository extends JpaRepository<Funding, Integer> {
    @Query(value = "SELECT * FROM fundings f WHERE f.end_date < CURRENT_TIMESTAMP + INTERVAL '72' HOUR ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Funding> findRandomFundings();

}
