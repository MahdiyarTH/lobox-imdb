package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.lobox.common.data.AbstractPersistableEnumConverter;
import org.lobox.common.data.PersistableEnum;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "product_person")
@IdClass(ProductPersonEntity.ProductPersonId.class)
public class ProductPersonEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "type")
    @Convert(converter = Type.Converter.class)
    private Type type;

    @Getter
    @RequiredArgsConstructor
    public enum Type implements PersistableEnum<Integer> {

        ACTOR(1),
        ACTRESS(2),
        SELF(3),
        DIRECTOR(4),
        WRITER(5),
        PRODUCER(6),
        COMPOSER(7),
        CINEMATOGRAPHER(8),
        EDITOR(9);

        private final Integer dbValue;

        public static Type fromString(String value) {
            return switch (value) {
                case "actor" -> Type.ACTOR;
                case "actress" -> Type.ACTRESS;
                case "self" -> Type.SELF;
                case "director" -> Type.DIRECTOR;
                case "writer" -> Type.WRITER;
                case "producer" -> Type.PRODUCER;
                case "composer" -> Type.COMPOSER;
                case "cinematographer" -> Type.CINEMATOGRAPHER;
                case "editor" -> Type.EDITOR;
                default -> null;
            };
        }

        public static class Converter extends AbstractPersistableEnumConverter<Type, Integer> {
            public Converter() {
                super(Type.class);
            }
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProductPersonId implements Serializable {

        private Long product;

        private Long person;

    }

}
