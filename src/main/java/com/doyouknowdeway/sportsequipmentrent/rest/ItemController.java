package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.ItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Age;
import com.doyouknowdeway.sportsequipmentrent.model.entity.Season;
import com.doyouknowdeway.sportsequipmentrent.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    public ItemDto createItem(@RequestBody @Valid final ItemCreateDto itemDto) {
        return itemService.createItem(itemDto);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{itemId}")
    public void updateItem(@RequestBody @Valid final ItemCreateDto itemDto, @PathVariable final int itemId) {
        itemService.updateItem(itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable final int itemId) {
        return itemService.getItemById(itemId);
    }

    @Secured("ROLE_ADMIN")
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

    @GetMapping("/season-filters")
    public Season[] getSeasonFilters() {
        return Season.values();
    }

    @GetMapping("/age-filters")
    public Age[] getAgeFilters() {
        return Age.values();
    }

}
