package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.ItemMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.ItemEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(final ItemRepository itemRepository, final ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto createItem(final ItemCreateDto itemDto) {
        final ItemEntity itemEntity = itemMapper.toEntity(itemDto);
        itemEntity.setCount(0);
        return itemMapper.fromEntity(itemRepository.save(itemEntity));
    }

    @Override
    public void updateItem(final ItemCreateDto itemDto, final int itemId) {
        final ItemEntity itemEntity = itemRepository.getById(itemId);
        itemMapper.merge(itemEntity, itemDto);
        itemRepository.save(itemEntity);
    }

    @Override
    public ItemDto getItemById(final int itemId) {
        return itemMapper.fromEntity(itemRepository.findById(itemId).orElseThrow());
    }

    @Override
    public void deleteItemById(final int itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> listItems(final String itemName) {
        if (itemName == null) {
            return itemMapper.fromEntities(itemRepository.findAll());
        }
        return itemMapper.fromEntities(itemRepository.findAll(ItemRepository.hasItemName(itemName)));
    }

    @Override
    public List<ItemDto> listItems(final String season, final String age) {
        return itemMapper.fromEntities(itemRepository.findAll(ItemRepository.hasSeasonAndAge(season, age)));
    }

}
