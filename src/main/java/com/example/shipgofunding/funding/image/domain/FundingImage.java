package com.example.shipgofunding.funding.image.domain;

import com.example.shipgofunding.config.utils.MetaData;
import com.example.shipgofunding.funding.domain.Funding;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE funding_images SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "funding_images")
public class FundingImage extends MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funding funding;

    @Column(nullable = false)
    private String fundingImage;

    @Builder
    public FundingImage(Funding funding, String fundingImage) {
        this.funding = funding;
        this.fundingImage = fundingImage;
    }

}
