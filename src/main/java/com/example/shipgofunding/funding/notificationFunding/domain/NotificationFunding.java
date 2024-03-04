package com.example.shipgofunding.funding.notificationFunding.domain;

import com.example.shipgofunding.config.utils.MetaData;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE notification_fundings SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "notification_fundings")
public class NotificationFunding extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public NotificationFunding(Funding funding, User user) {
        this.funding = funding;
        this.user = user;
    }
}
