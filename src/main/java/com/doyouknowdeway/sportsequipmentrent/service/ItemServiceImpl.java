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
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto createItem(ItemCreateDto itemDto) {
        ItemEntity itemEntity = itemMapper.toEntity(itemDto);
        itemEntity.setCount(0);
        return itemMapper.fromEntity(itemRepository.save(itemEntity));
    }

    @Override
    public void updateItem(ItemCreateDto itemDto, int itemId) {
        ItemEntity itemEntity = itemRepository.getById(itemId);
        itemMapper.merge(itemEntity, itemDto);
        itemRepository.save(itemEntity);
    }

    @Override
    public ItemDto getItemById(int itemId) {
        return itemMapper.fromEntity(itemRepository.findById(itemId).orElseThrow());
    }

    @Override
    public void deleteItemById(int itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> listItems(String itemName) {
        if (itemName == null) {
            return itemMapper.fromEntities(itemRepository.findAll());
        }
        return itemMapper.fromEntities(itemRepository.findAll(ItemRepository.hasItemName(itemName)));
    }

    @Override
    public List<ItemDto> listItems(String season, String age) {
        return itemMapper.fromEntities(itemRepository.findAll(ItemRepository.hasSeasonAndAge(season, age)));
    }

}
