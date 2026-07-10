package org.lobox.common.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public abstract class AbstractPersistableEnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    public AbstractPersistableEnumConverter(Class<T> clazz) {
        this.clazz = clazz;

        T[] enums = clazz.getEnumConstants();
        Map<E, Integer> enumsMap = new HashMap<>();

        for (T e : enums)
            enumsMap.compute(e.getDbValue(), (key, value) -> value == null ? 1 : value + 1);

        for (Map.Entry<E, Integer> entry : enumsMap.entrySet()) {
            if (entry.getValue() > 1)
                throw new IllegalStateException(String.format("Duplicate value '%s' for enum class %s", entry.getKey(), clazz.getName()));
        }

    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getDbValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        if (dbData == null)
            return null;

        T[] enums = clazz.getEnumConstants();

        for (T e : enums) {
            if (e.getDbValue().equals(dbData)) {
                return e;
            }
        }

        throw new IllegalArgumentException("Cannot convert database value '" + dbData + "' to " + clazz.getName());
    }
}
