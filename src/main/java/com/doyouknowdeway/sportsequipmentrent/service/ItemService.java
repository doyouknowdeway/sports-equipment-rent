package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto);

    void updateItem(ItemDto itemDto, int itemId);

    ItemDto getItemById(int itemId);

    void deleteItemById(int itemId);

    List<ItemDto> listItems(String itemName);

    List<ItemDto> listItems(String season, String age);

}
