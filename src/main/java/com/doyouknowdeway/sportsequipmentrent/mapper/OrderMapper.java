package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.OrderCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto fromEntity(OrderEntity entity);

    @Mapping(target = "profile.id", source = "profileId")
    OrderEntity toEntity(OrderCreateDto dto);

    void merge(@MappingTarget OrderEntity entity, OrderCreateDto dto);

    List<OrderDto> fromEntities(Iterable<OrderEntity> entities);

}
