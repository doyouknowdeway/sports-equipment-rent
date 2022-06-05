package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.ItemMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto createItem(final ItemCreateDto itemDto) {
        final ItemEntity itemEntity = itemMapper.itemCreateDtoToItemEntity(itemDto);
        itemEntity.setCount(0);
        final ItemDto itemDtoResult = itemMapper.itemEntityToItemDto(itemRepository.save(itemEntity));
        log.info("Item with id = {} has been created.", itemDtoResult.getId());
        return itemDtoResult;
    }

    @Override
    public void updateItem(final ItemCreateDto itemDto, final int itemId) {
        final ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found!"));
        itemMapper.mergeItemEntityAndItemCreateDto(itemEntity, itemDto);
        itemRepository.save(itemEntity);
        log.info("Item with id = {} has been updated.", itemId);
    }

    @Override
    public ItemDto getItemById(final int itemId) {
        final Optional<ItemEntity> itemEntity = itemRepository.findById(itemId);
        itemEntity.ifPresentOrElse(
                (user) -> log.info("Item with id = {} has been found.", user.getId()),
                () -> log.warn("Item with id = {} hasn't been found.", itemId));
        return itemEntity.map(itemMapper::itemEntityToItemDto)
                .orElseThrow(() -> new EntityNotFoundException("Item not found!"));
    }

    @Override
    public void deleteItemById(final int itemId) {
        final Optional<ItemEntity> itemEntity = itemRepository.findById(itemId);
        itemEntity.ifPresentOrElse(
                (user) -> log.info("Item with id = {} has been found.", itemId),
                () -> {
                    log.warn("Item with id = {} hasn't been found.", itemId);
                    throw new EntityNotFoundException("Item not found!");
                });
        itemRepository.deleteById(itemId);
        log.info("Item with id = {} has been deleted.", itemId);
    }

    @Override
    public List<ItemDto> listItems(final String itemName) {
        if (itemName == null) {
            final List<ItemEntity> itemEntities = itemRepository.findAll();
            log.info("There have been found {} items.", itemEntities.size());
            return itemMapper.itemEntitiesToItemDtoList(itemEntities);
        }
        final List<ItemEntity> filteredItemEntities = itemRepository.findAll(ItemRepository.hasItemName(itemName));
        log.info("There have been found {} filtered items.", filteredItemEntities.size());
        return itemMapper.itemEntitiesToItemDtoList(filteredItemEntities);
    }

    @Override
    public List<ItemDto> listItems(final String season, final String age) {
        final List<ItemEntity> filteredItemEntities = itemRepository.findAll(ItemRepository.hasSeasonAndAge(season, age));
        log.info("There have been found {} filtered items.", filteredItemEntities.size());
        return itemMapper.itemEntitiesToItemDtoList(filteredItemEntities);
    }

}
