package com.example.shipgofunding.funding.domain;

import com.example.shipgofunding.config.utils.MetaData;
import com.example.shipgofunding.funding.request.FundingRequest;
import com.example.shipgofunding.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE fundings SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "fundings")
public class Funding extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String fundingTitle;

    @Column(nullable = false)
    private String fundingSummary;

    @Column(nullable = false)
    private String fundingDescription;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer individualPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FundingEnum fundingEnum;

    @Column(nullable = false)
    private String fundingStatusDescription;

    @Formula("(SELECT COUNT(*) FROM funding_hearts fh WHERE fh.funding_id = id)")
    private int likesCount;

    @Builder
    public Funding (User user, String fundingTitle, String fundingSummary, String fundingDescription, String category, Integer individualPrice, Integer totalPrice, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = user;
        this.fundingTitle = fundingTitle;
        this.fundingSummary = fundingSummary;
        this.fundingDescription = fundingDescription;
        this.category = category;
        this.individualPrice = individualPrice;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;

        updateStatus();
    }

    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, endDate);
        long hours = duration.toHours();

        if ( now.isBefore(startDate) ) {
            this.fundingEnum = FundingEnum.OPEN_SCHEDULED;
        }
        else if (hours >= 0 && hours <= 72) { // endDate가 현재 시간으로부터 72시간 이내인 경우
            this.fundingEnum = FundingEnum.CLOSE_IMMINENT;
        }
        else if ( now.isAfter(startDate) && now.isBefore(endDate) ) {
            this.fundingEnum = FundingEnum.IN_PROGRESS;
        }
        else if ( now.isAfter(endDate) ) {
            this.fundingEnum = FundingEnum.FUNDING_CLOSED;
        }

        this.fundingStatusDescription = this.fundingEnum.getDescription();
    }


    public void updateFunding(FundingRequest.UpdateFundingRequestDTO requestDTO) {
        this.category = requestDTO.getCategory();
        this.startDate = requestDTO.getStartDate();
        this.endDate = requestDTO.getEndDate();
        this.totalPrice = requestDTO.getTotalPrice();
        this.individualPrice = requestDTO.getIndividualPrice();
        this.fundingTitle = requestDTO.getFundingTitle();
        this.fundingSummary = requestDTO.getFundingSummary();
        this.fundingDescription = requestDTO.getFundingDescription();

        updateStatus();
    }
}
