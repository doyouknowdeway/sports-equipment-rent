package com.doyouknowdeway.sportsequipmentrent.converter;

import com.doyouknowdeway.sportsequipmentrent.model.entity.Age;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AgeConverter implements AttributeConverter<Age, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Age attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Age convertToEntityAttribute(final Integer dbData) {
        return Age.of(dbData).orElse(null);
    }

}
