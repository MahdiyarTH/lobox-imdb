package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_rating")
@IdClass(ProductRatingEntity.ProductRatingId.class)
public class ProductRatingEntity {


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "count")
    private Integer count;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProductRatingId implements Serializable {

        private String product;

    }

}
