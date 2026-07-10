package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "person",
        indexes = {
                @Index(name = "idx_nconst", columnList = "nconst")
        }
)
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(
            name = "person_seq",
            sequenceName = "person_seq"
    )
    private Long id;

    @Column(name = "nconst")
    private String nconst;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_alive")
    private boolean isAlive;

    public PersonEntity(String nconst, String fullName, boolean isAlive) {
        this.fullName = fullName;
        this.nconst = nconst;
        this.isAlive = isAlive;
    }
}
