package com.example.shipgofunding.config.utils;

import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.repository.FundingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FundingStatusScheduler {

    @Autowired
    private FundingJpaRepository fundingJpaRepository;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFundingStatus() {
        List<Funding> fundings = fundingJpaRepository.findAll();

        for ( Funding funding : fundings ) {
            funding.updateStatus();
            fundingJpaRepository.save(funding);
        }
    }
}
