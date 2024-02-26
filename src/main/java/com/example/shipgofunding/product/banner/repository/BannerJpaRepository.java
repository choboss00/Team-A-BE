package com.example.shipgofunding.product.banner.repository;

import com.example.shipgofunding.product.banner.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerJpaRepository extends JpaRepository<Banner, Integer> {

    @Query(value = "SELECT * FROM banner ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Banner> findRandomBanners();

}
