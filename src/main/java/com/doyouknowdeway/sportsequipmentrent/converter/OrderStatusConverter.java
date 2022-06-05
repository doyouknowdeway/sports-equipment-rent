package com.doyouknowdeway.sportsequipmentrent.converter;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final OrderStatus attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public OrderStatus convertToEntityAttribute(final Integer dbData) {
        return OrderStatus.of(dbData).orElse(null);
    }

}
