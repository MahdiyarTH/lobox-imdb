package org.lobox.imdb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

}
