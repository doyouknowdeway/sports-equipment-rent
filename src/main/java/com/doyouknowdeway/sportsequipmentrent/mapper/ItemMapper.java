package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto itemEntityToItemDto(ItemEntity entity);

    ItemEntity itemCreateDtoToItemEntity(ItemCreateDto dto);

    void mergeItemEntityAndItemCreateDto(@MappingTarget ItemEntity entity, ItemCreateDto dto);

    List<ItemDto> itemEntitiesToItemDtoList(Iterable<ItemEntity> entities);

}
