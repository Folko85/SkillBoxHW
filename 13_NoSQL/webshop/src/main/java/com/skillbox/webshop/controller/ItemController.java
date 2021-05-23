package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.service.ItemService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@Tag(name = "Контроллер операций с товарами")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    @Operation(summary = "Получение всех товаров")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Кроме стандартного круд у нас будет получение всех товаров для определённого магазина
    @GetMapping("/list/{shopId}")
    @Operation (summary = "Список товаров в конкретном магазине")
    public List<Item> getItemsInShop(@PathVariable String shopId) {
        return itemService.getItemsInShop(shopId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение товара по id")
    public Item getItemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Создание товара")
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping ("/{id}/update")
    @Operation(summary = "Обновление данных о товаре")
    public Item updateItem(@PathVariable String id, @RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление товара")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

}
