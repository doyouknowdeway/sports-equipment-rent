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
    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ItemDto createItem(@RequestBody ItemCreateDto itemDto) {
        return service.createItem(itemDto);
    }

    @PutMapping("/{itemId}")
    public void updateItem(@RequestBody ItemCreateDto itemDto, @PathVariable int itemId) {
        service.updateItem(itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable int itemId) {
        return service.getItemById(itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable int itemId) {
        service.deleteItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestParam(value = "itemName", required = false) String itemName) {
        return service.listItems(itemName);
    }

    @GetMapping("/filtered")
    public List<ItemDto> getAllItemsBySeasonAndAge(@RequestParam(value = "season", required = false) String season,
                                                   @RequestParam(value = "age", required = false) String age) {
        return service.listItems(season, age);
    }

}
