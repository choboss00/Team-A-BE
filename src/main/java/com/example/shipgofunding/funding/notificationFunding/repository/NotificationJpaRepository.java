package com.example.shipgofunding.funding.notificationFunding.repository;

import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.notificationFunding.domain.NotificationFunding;
import com.example.shipgofunding.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationFunding, Integer> {

    boolean existsByFundingIdAndUserId(int fundingId, Long id);
}
