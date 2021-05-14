package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("item")
@Tag(name = "Item", description = "The Item API")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Получить список товаров", tags = "Item")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the items",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Item.class)))
                    })
    })
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Кроме стандартного круд у нас будет получение всех товаров для определённого магазина
    @GetMapping("{shopId}")
    public List<Item> getItemsInShop(@PathVariable String shopId) {
        return itemService.getItemsInShop(shopId);
    }

    // а также, требуемая по заданию статистика для магазина
    @GetMapping("{shopId}")
    public String getStatisticsForShop(@PathVariable String shopId) {
        return "";
    }


    @GetMapping("{id}")
    public Item getOneItem(@PathVariable String id) {
        return itemService.getOneItem();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping
    public Item updateItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

}


/**
 * ToDo: add swagger
 */
