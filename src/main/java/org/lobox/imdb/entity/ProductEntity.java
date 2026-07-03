package org.lobox.imdb.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "product",
        indexes = {
                @Index(name = "idx_tconst", columnList = "tconst")
        }
)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tconst")
    private String tconst;

    @Column(name = "title")
    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

}
