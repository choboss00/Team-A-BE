package com.example.shipgofunding.funding.domain;

import com.example.shipgofunding.config.utils.MetaData;
import com.example.shipgofunding.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();

        if ( now.isBefore(startDate) ) {
            this.fundingEnum = FundingEnum.OPEN_SCHEDULED;
        }
        else if ( now.isAfter(startDate) && now.isBefore(endDate) ) {
            this.fundingEnum = FundingEnum.IN_PROGRESS;
        }
        else if ( now.isAfter(endDate) ) {
            this.fundingEnum = FundingEnum.FUNDING_CLOSED;
        }
        else {
            Duration duration = Duration.between(now, endDate);
            long hours = duration.toHours();

            if (hours >= 0 && hours <= 72) { // endDate가 현재 시간으로부터 72시간 이내인 경우
                this.fundingEnum = FundingEnum.CLOSE_IMMINENT;
            }
        }
    }

}
