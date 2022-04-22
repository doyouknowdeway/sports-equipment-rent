package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderItemEntity;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "itemId", source = "item.id")
    OrderItemDto fromEntity(OrderItemEntity entity);

    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "item.id", source = "itemId")
    OrderItemEntity toEntity(OrderItemDto dto);

}
