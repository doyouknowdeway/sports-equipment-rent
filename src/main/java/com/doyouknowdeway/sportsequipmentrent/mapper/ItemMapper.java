package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto fromEntity(ItemEntity entity);

    ItemEntity toEntity(ItemDto dto);

    void merge(ItemDto dto, @MappingTarget ItemEntity entity);

    List<ItemDto> fromEntities(Iterable<ItemEntity> entities);

}
