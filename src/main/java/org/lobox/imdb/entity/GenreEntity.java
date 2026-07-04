package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @SequenceGenerator(
            name = "genre_seq",
            sequenceName = "genre_seq"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    public GenreEntity(String title) {
        this.title = title;
    }

}
