package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ItemDto createItem(@RequestBody final ItemCreateDto itemDto) {
        return itemService.createItem(itemDto);
    }

    @PutMapping("/{itemId}")
    public void updateItem(@RequestBody final ItemCreateDto itemDto, @PathVariable final int itemId) {
        itemService.updateItem(itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable final int itemId) {
        return itemService.getItemById(itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable final int itemId) {
        itemService.deleteItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestParam(value = "itemName", required = false) final String itemName) {
        return itemService.listItems(itemName);
    }

    @GetMapping("/filtered")
    public List<ItemDto> getAllItemsBySeasonAndAge(@RequestParam(value = "season", required = false) final String season,
                                                   @RequestParam(value = "age", required = false) final String age) {
        return itemService.listItems(season, age);
    }

}
