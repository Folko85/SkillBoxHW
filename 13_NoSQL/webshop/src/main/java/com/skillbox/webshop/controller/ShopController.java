package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("shop")
public class ShopController {

    @GetMapping
    public List<Shop> getAllShops() {
        // some doing
        return new ArrayList<>();
    }

    @GetMapping("{id}")
    public Shop getCurrentShop (@PathVariable String id) {
        // some doing
        return new Shop();
    }

    @PostMapping
    public Shop addShop (@RequestBody Shop shop) {
        // some doing
        return shop;
    }

    // Хоть запрос ограничивается переменными и не содержит тела, но суть запроса в добавлении информации, поэтому post
    // Этот метод будет добавлять товар в количестве amount магазину
    @PostMapping()
    public List<Item> addItemToShop (@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount ) {
        // some doing
        return new ArrayList<>();
    }

    // Этот метод будет изменять количество товара в магазине
    @PutMapping()
    public List<Item> changeItemInShop (@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount ) {
        // some doing
        return new ArrayList<>();
    }

    @PutMapping
    public Shop updateShopInfo (@RequestBody Shop shop) {
        // some doing
        return shop;
    }

    @DeleteMapping("{id}")
    public void removeShop(@PathVariable String id) {
        // some doing
    }

}
