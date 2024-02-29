package com.example.shipgofunding.funding.banner.repository;

import com.example.shipgofunding.funding.banner.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerJpaRepository extends JpaRepository<Banner, Integer> {

    @Query(value = "SELECT * FROM banners ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Banner> findRandomBanners();

}
