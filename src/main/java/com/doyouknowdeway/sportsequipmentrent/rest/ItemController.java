package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
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
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        return service.createItem(itemDto);
    }

    @PutMapping("/update/{itemId}")
    public void updateItem(@RequestBody ItemDto itemDto, @PathVariable int itemId) {
        service.updateItem(itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable int itemId) {
        return service.getItemById(itemId);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteItemById(@PathVariable int itemId) {
        service.deleteItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestParam(value = "itemName", required = false) String itemName) {
        return service.listItems(itemName);
    }

    @GetMapping("/filtered")
    public List<ItemDto> getAllItemsBySeasonAndAge(@RequestParam("season") String season,
                                                   @RequestParam("age") String age) {
        return service.listItems(season, age);
    }

}
