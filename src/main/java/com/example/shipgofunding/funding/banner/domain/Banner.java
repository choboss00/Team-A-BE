package com.example.shipgofunding.funding.banner.domain;

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
@SQLDelete(sql = "UPDATE banners SET deleted_at = CURRENT_TIMESTAMP, is_deleted = TRUE where id = ?")
@Entity
@Table(name = "banners")
public class Banner extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Funding funding;

    @Column(nullable = false)
    private String image;

    @Builder
    public Banner(Funding funding, String image) {
        this.funding = funding;
        this.image = image;
    }

}
