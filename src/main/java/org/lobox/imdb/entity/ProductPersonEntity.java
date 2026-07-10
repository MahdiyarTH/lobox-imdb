package org.lobox.imdb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.lobox.common.data.AbstractPersistableEnumConverter;
import org.lobox.common.data.PersistableEnum;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Id
    @Column(name = "type")
//    @Convert(converter = Type.Converter.class)
    private Type type;

    @Getter
    @RequiredArgsConstructor
    public enum Type implements PersistableEnum<Integer> {

        ACTOR(1),
        DIRECTOR(4),
        WRITER(5);

        private final Integer dbValue;

        public static Type fromString(String value) {
            return switch (value) {
                case "actor", "actress" -> Type.ACTOR;
                case "director" -> Type.DIRECTOR;
                case "writer" -> Type.WRITER;
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

        private String product;

        private Long person;

        private Type type;

    }

}
