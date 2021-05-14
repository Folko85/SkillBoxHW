package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("{id}")
    public Shop getCurrentShop(@PathVariable String id) {
        return shopService.getCurrentShop(id);
    }

    @PostMapping
    public Shop addShop(@RequestBody Shop shop) {
        return shopService.saveShop(shop);
    }

    // Хоть запрос ограничивается переменными и не содержит тела, но суть запроса в добавлении информации, поэтому post
    // Этот метод будет выставлять товар в количестве amount в магазин
    @PostMapping()
    public List<Item> exposeItemToShop(@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount) {

        return shopService.saveItemToShop(shopId, itemId, amount);
    }

    // Этот метод будет изменять количество товара в магазине
    @PutMapping()
    public List<Item> changeItemInShop(@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount) {
        // some doing
        return shopService.saveItemToShop(shopId, itemId, amount);
    }

    @PutMapping
    public Shop updateShopInfo(@RequestBody Shop shop) {
        // some doing
        return shopService.saveShop(shop);
    }

    @DeleteMapping("{id}")
    public void removeShop(@PathVariable String id) {
            shopService.deleteShop(id);
    }
}

/**
 * ToDo: add swagger
 */
