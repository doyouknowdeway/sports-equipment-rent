package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderUpdateStatusDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto orderEntityToOrderDto(OrderEntity entity);

    @Mapping(target = "profile.id", source = "profileId")
    OrderEntity orderCreateDtoToOrderEntity(OrderCreateDto dto);

    void mergeOrderEntityAndOrderCreateDto(@MappingTarget OrderEntity entity, OrderCreateDto dto);

    void mergeOrderEntityAndOrderUpdateStatusDto(@MappingTarget OrderEntity orderEntity, OrderUpdateStatusDto orderDto);

    List<OrderDto> orderEntitiesToOrderDtoList(Iterable<OrderEntity> entities);

}
