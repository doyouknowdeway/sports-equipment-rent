package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @Autowired
    public ItemController(final ItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ItemDto createItem(@RequestBody final ItemCreateDto itemDto) {
        return service.createItem(itemDto);
    }

    @PutMapping("/{itemId}")
    public void updateItem(@RequestBody final ItemCreateDto itemDto, @PathVariable final int itemId) {
        service.updateItem(itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable final int itemId) {
        return service.getItemById(itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable final int itemId) {
        service.deleteItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestParam(value = "itemName", required = false) final String itemName) {
        return service.listItems(itemName);
    }

    @GetMapping("/filtered")
    public List<ItemDto> getAllItemsBySeasonAndAge(@RequestParam(value = "season", required = false) final String season,
                                                   @RequestParam(value = "age", required = false) final String age) {
        return service.listItems(season, age);
    }

}
