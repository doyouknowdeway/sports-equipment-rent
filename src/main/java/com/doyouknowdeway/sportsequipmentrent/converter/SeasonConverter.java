package com.doyouknowdeway.sportsequipmentrent.converter;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Season;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SeasonConverter implements AttributeConverter<Season, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Season attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Season convertToEntityAttribute(Integer dbData) {
        return Season.of(dbData).orElse(null);
    }

}
