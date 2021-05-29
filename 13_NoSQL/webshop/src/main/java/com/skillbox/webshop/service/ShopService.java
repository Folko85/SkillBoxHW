package com.skillbox.webshop.service;

import com.skillbox.webshop.dto.ShopModel;
import com.skillbox.webshop.exception.DuplicateEntityException;
import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.mapper.ShopMapper;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ItemService itemService;

    public ShopService(ShopRepository shopRepository, ItemService itemService) {
        this.shopRepository = shopRepository;
        this.itemService = itemService;
    }

    public List<ShopModel> getAllShops() {
        return shopRepository.findAll().stream().map(ShopMapper::map).collect(Collectors.toList());
    }

    public Shop getCurrentShop(String id) {
        return shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Магазин не найден"));
    }

    public ShopModel saveShop(Shop shop) {
        return ShopMapper.map(shopRepository.save(shop));
    }

    public String saveItemToShop(String shopId, String itemId) {
        Item existingItem = itemService.getItemById(itemId);
        Shop existingShop = this.getCurrentShop(shopId);

        if (existingShop.getItems().contains(existingItem)){
            throw new DuplicateEntityException("Такой товар уже выставлен в этот магазин");
        }
        existingShop.getItems().add(existingItem);
        this.saveShop(existingShop);
        return "Товар " + existingItem.getName() +
                " успешно выставлен в магазин " +
                existingShop.getName();
    }

    public void deleteShop(String id) {
        if (shopRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Магазин не найден");
        } else shopRepository.deleteById(id);
    }

    public void deleteItemFromShop(String itemId, String shopId) {
        Item existingItem = itemService.getItemById(itemId);
        Shop existingShop = this.getCurrentShop(shopId);
        if (existingShop.getItems().contains(existingItem)){
            existingShop.getItems().remove(existingItem);
            this.saveShop(existingShop);
        } else {
            throw new EntityNotFoundException("Такой товар не был выставлен");
        }
    }

    public String getStatisticsForShop() {
        /**
         *      *  Команда должна выводить для каждого магазина:
         *      *
         *      * общее количество наименований товаров,
         *      * среднюю цену товаров,
         *      * самый дорогой и самый дешевый товар,
         *      * количество товаров дешевле 100 рублей.
         */
        return "";
    }
}
