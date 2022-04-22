package com.doyouknowdeway.sportsequipmentrent.converter;

import com.doyouknowdeway.sportsequipmentrent.model.entity.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        return Role.of(dbData).orElse(null);
    }

}
