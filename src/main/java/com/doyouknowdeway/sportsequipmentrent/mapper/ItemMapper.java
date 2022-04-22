package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto fromEntity(ItemEntity entity);

    ItemEntity toEntity(ItemCreateDto dto);

    void merge(@MappingTarget ItemEntity entity, ItemCreateDto dto);

    List<ItemDto> fromEntities(Iterable<ItemEntity> entities);

}
