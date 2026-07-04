package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crew")
public class CrewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crew_seq")
    @SequenceGenerator(
            name = "crew_seq",
            sequenceName = "crew_seq"
    )
    private Long id;

    @Column(name = "nconst")
    private String nconst;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_alive")
    private boolean isAlive;

    public CrewEntity(String nconst, String fullName, boolean isAlive) {
        this.fullName = fullName;
        this.nconst = nconst;
        this.isAlive = isAlive;
    }
}
