package com.skillbox.webshop.controller;

import com.skillbox.webshop.dto.ShopModel;
import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.mapper.ShopMapper;
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

    @GetMapping("/all")
    @Operation(summary = "Получить список магазинов")
    public List<ShopModel> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию по конкретному магазину")
    public ShopModel getCurrentShop(@PathVariable String id) {
        return ShopMapper.map(shopService.getCurrentShop(id)) ;
    }

    // а также, требуемая по заданию статистика для магазина
    @GetMapping("/{shopId}/info")
    @Operation(summary = "Статистика магазина")
    public String getStatisticsForShop(@PathVariable String shopId) {
        return shopService.getStatisticsForShop(shopId);
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Добавить магазин в список")
    public ShopModel addShop(@RequestBody ShopModel shopModel) {
        return shopService.saveShop(ShopMapper.reverseMap(shopModel));
    }

    // Хоть запрос ограничивается переменными и не содержит тела, но суть запроса в добавлении информации, поэтому post
    // Этот метод будет выставлять товар в магазин
    @PostMapping("/expose")
    @Operation(summary = "Добавить товар в магазин")
    public String exposeItemToShop(@RequestParam String shopId, @RequestParam String itemId) {
        return shopService.saveItemToShop(shopId, itemId);
    }

    @PutMapping("/update")
    @Operation(summary = "Изменить данные магазина")
    public ShopModel updateShopInfo(@RequestBody ShopModel shopModel) {
        if (shopModel.getId() == null) {
            throw new EntityNotFoundException("Идентификатор изменяемой сущности не может быть null");
        } else {
            Shop existingShop = shopService.getCurrentShop(shopModel.getId());
            existingShop = existingShop.setName(shopModel.getName()).setAddress(shopModel.getAddress());
            return shopService.saveShop(existingShop);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить магазин из списка")
    public void removeShop(@PathVariable String id) {
        shopService.deleteShop(id);
    }

    @DeleteMapping("/delete/item")
    @Operation(summary = "Удалить товар из магазина")
    public void removeItemFromShop(@RequestParam String itemId, @RequestParam String shopId) {
        shopService.deleteItemFromShop(itemId, shopId);
    }
}
