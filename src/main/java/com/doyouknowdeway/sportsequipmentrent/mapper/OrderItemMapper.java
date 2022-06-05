package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderItemEntity;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "item.id", source = "itemId")
    OrderItemEntity orderItemDtoToOrderItemEntity(OrderItemDto dto);

}
