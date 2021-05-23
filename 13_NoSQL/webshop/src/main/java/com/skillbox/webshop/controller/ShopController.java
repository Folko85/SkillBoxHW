package com.skillbox.webshop.controller;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@Tag(name = "Контроллер операций с магазинами")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping()
    @Operation (summary = "Получить список магазинов")
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    @Operation (summary = "Получить информацию по конкретному магазину")
    public Shop getCurrentShop(@PathVariable String id) {
        return shopService.getCurrentShop(id);
    }

    // а также, требуемая по заданию статистика для магазина
    @GetMapping("/{id}/info")
    @Operation(summary = "Статистика магазина")
    public String getStatisticsForShop(@PathVariable String shopId) {
        return "";
    }

    @PostMapping(value = "/create")
    @Operation (summary = "Добавить магазин в список")
    public Shop addShop(@RequestBody Shop shop) {
        return shopService.saveShop(shop);
    }

    // Хоть запрос ограничивается переменными и не содержит тела, но суть запроса в добавлении информации, поэтому post
    // Этот метод будет выставлять товар в количестве amount в магазин
    @PostMapping("/expose")
    @Operation (summary = "Добавить товар в количестве amount в магазин")
    public List<Item> exposeItemToShop(@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount) {

        return shopService.saveItemToShop(shopId, itemId, amount);
    }

    // Этот метод будет изменять количество товара в магазине
    @PutMapping("/expose/update")
    @Operation (summary = "Изменить количество товара в магазине")
    public List<Item> changeItemInShop(@RequestParam String shopId, @RequestParam String itemId, @RequestParam String amount) {
        // some doing
        return shopService.saveItemToShop(shopId, itemId, amount);
    }

    @PutMapping("/{id}/update")
    @Operation (summary = "Изменить данные магазина")
    public Shop updateShopInfo(@PathVariable String id, @RequestBody Shop shop) {
        // some doing
        return shopService.saveShop(shop);
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Удалить магазин из списка")
    public void removeShop(@PathVariable String id) {
            shopService.deleteShop(id);
    }
}
