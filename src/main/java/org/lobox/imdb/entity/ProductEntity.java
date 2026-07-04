package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "product",
        indexes = {
                @Index(name = "idx_tconst", columnList = "tconst")
        }
)
@Setter
@Getter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_seq"
    )
    private Long id;

    @Column(name = "tconst")
    private String tconst;

    @Column(name = "hello", length = 512)
    private String title;

    @Column(name = "original_title2", length = 512)
    private String originalTitle;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    public static ProductEntity fromSplit(String[] split) {
        final ProductEntity product = new ProductEntity();
        product.setTconst(split[0]);
        product.setTitle(split[2]);
        product.setOriginalTitle(split[3]);
        product.setStartYear(split[5].equals("\\N") ? null : Integer.parseInt(split[5]));
        product.setEndYear(split[6].equals("\\N") ? null : Integer.parseInt(split[6]));
        product.setRuntimeMinutes(split[7].equals("\\N") ? null : Integer.parseInt(split[7]));

        return product;
    }
}
