package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    @GetMapping
    public List<Item> getAllItems() {
        // some doing
        return new ArrayList<>();
    }

    // Кроме стандартного круд у нас будет получение всех товаров для определённого магазина
    @GetMapping ("{shop}")
    public List<Item> getItemsInShop(@PathVariable String shop) {
        // some doing
        return new ArrayList<>();
    }

    @GetMapping("{id}")
    public Item getOneItem (@PathVariable String id) {
        // some doing
        return new Item();
    }

    @PostMapping
    public Item createItem (@RequestBody Item item) {
        // some doing
        return item;
    }

    @PutMapping
    public Item updateItem (@RequestBody Item item) {
        // some doing
        return item;
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable String id) {
        // some doing
    }

}
