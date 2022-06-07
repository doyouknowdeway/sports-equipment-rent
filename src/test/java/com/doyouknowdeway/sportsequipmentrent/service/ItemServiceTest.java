package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.ItemMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Age;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Season;
import com.doyouknowdeway.sportsequipmentrent.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Spy
    private ItemMapperImpl itemMapperImpl;

    @Mock
    private ItemRepository itemRepositoryMock;

    @InjectMocks
    private ItemServiceImpl itemServiceImpl;

    private final int itemId = 1;
    private final String name = "ski";
    private final String description = "the coolest ski";

    private final ItemEntity itemEntity = ItemEntity.builder()
            .name(name)
            .description(description)
            .age(Age.OVER_15)
            .season(Season.AUTUMN)
            .image(new byte[0])
            .build();

    private final ItemCreateDto itemCreateDto = ItemCreateDto.builder()
            .name(name)
            .description(description)
            .age(Age.OVER_15)
            .season(Season.AUTUMN)
            .base64StringImage("MHg=")
            .build();

    private final ItemDto itemDto = ItemDto.builder()
            .name(name)
            .description(description)
            .age(Age.OVER_15)
            .season(Season.AUTUMN)
            .build();

    @Test
    public void createItem() {
        // given
        when(itemMapperImpl.itemCreateDtoToItemEntity(itemCreateDto)).thenReturn(itemEntity);
        when(itemRepositoryMock.save(itemEntity)).thenReturn(itemEntity);

        // when
        itemServiceImpl.createItem(itemCreateDto);

        // then
        verify(itemRepositoryMock, times(1)).save(itemEntity);
    }

    @Test
    public void updateItem() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.of(itemEntity));
        doNothing().when(itemMapperImpl).mergeItemEntityAndItemCreateDto(itemEntity, itemCreateDto);
        when(itemRepositoryMock.save(itemEntity)).thenReturn(itemEntity);

        // when
        itemServiceImpl.updateItem(itemCreateDto, itemId);

        // then
        verify(itemRepositoryMock, times(1)).save(itemEntity);
    }

    @Test
    public void updateItemWhenOptionalNull() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> itemServiceImpl.updateItem(itemCreateDto, itemId));
        verify(itemRepositoryMock, never()).save(itemEntity);
        assertEquals(actualException.getMessage(), "Item not found!");
    }

    @Test
    public void getItemById() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.of(itemEntity));
        when(itemMapperImpl.itemEntityToItemDto(itemEntity)).thenReturn(itemDto);

        // when
        final ItemDto itemDtoActual = itemServiceImpl.getItemById(itemId);

        // then
        verify(itemRepositoryMock, times(1)).findById(itemId);
        assertEquals(itemDto, itemDtoActual);
    }

    @Test
    public void getItemByIdWhenOptionalNull() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> itemServiceImpl.getItemById(itemId));
        verify(itemRepositoryMock, times(1)).findById(itemId);
        assertEquals(actualException.getMessage(), "Item not found!");
    }

    @Test
    public void deleteItemById() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.of(itemEntity));

        // when
        itemServiceImpl.deleteItemById(itemId);

        // then
        verify(itemRepositoryMock, times(1)).findById(itemId);
        verify(itemRepositoryMock, times(1)).deleteById(itemId);
    }

    @Test
    public void deleteItemByIdWhenOptionalNull() {
        // given
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> itemServiceImpl.deleteItemById(itemId));
        verify(itemRepositoryMock, times(1)).findById(itemId);
        assertEquals(actualException.getMessage(), "Item not found!");
    }

    @Test
    public void listItems() {
        // given
        when(itemRepositoryMock.findAll()).thenReturn(List.of(itemEntity));
        when(itemMapperImpl.itemEntitiesToItemDtoList(List.of(itemEntity))).thenReturn(List.of(itemDto));

        // when
        itemServiceImpl.listItems(null);

        // then
        verify(itemRepositoryMock, times(1)).findAll();
        verify(itemRepositoryMock, never()).findAll(ItemRepository.hasItemName(name));
    }

    @Test
    public void listFilteredItemsByName() {
        // given
        when(itemRepositoryMock.findAll((Specification<ItemEntity>) any())).thenReturn(List.of(itemEntity));
        when(itemMapperImpl.itemEntitiesToItemDtoList(List.of(itemEntity))).thenReturn(List.of(itemDto));

        // when
        itemServiceImpl.listItems(name);

        // then
        verify(itemRepositoryMock, never()).findAll();
        verify(itemRepositoryMock, times(1)).findAll((Specification<ItemEntity>) any());
    }

    @Test
    public void listFilteredItemsBySeasonAndAge() {
        // given
        when(itemRepositoryMock.findAll((Specification<ItemEntity>) any()))
                .thenReturn(List.of(itemEntity));
        when(itemMapperImpl.itemEntitiesToItemDtoList(List.of(itemEntity))).thenReturn(List.of(itemDto));

        // when
        itemServiceImpl.listItems(name);

        // then
        verify(itemRepositoryMock, times(1))
                .findAll((Specification<ItemEntity>) any());
    }

}
