package com.doyouknowdeway.sportsequipmentrent.mapper;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "base64StringImage", source = "image", qualifiedByName = "mapBytesArray")
    ItemDto itemEntityToItemDto(ItemEntity entity);

    @Mapping(target = "image", source = "base64StringImage", qualifiedByName = "base64StringImage")
    ItemEntity itemCreateDtoToItemEntity(ItemCreateDto dto);

    @Mapping(target = "image", source = "base64StringImage", qualifiedByName = "base64StringImage")
    void mergeItemEntityAndItemCreateDto(@MappingTarget ItemEntity entity, ItemCreateDto dto);

    @Mapping(target = "base64StringImage", source = "image", qualifiedByName = "mapBytesArray")
    List<ItemDto> itemEntitiesToItemDtoList(Iterable<ItemEntity> entities);

    @Named(value = "mapBytesArray")
    default String mapBytesArray(final byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

    @Named(value = "base64StringImage")
    default byte[] mapBase64StringImage(final String base64StringImage) {
        return Base64.getDecoder().decode(base64StringImage);
    }

}
