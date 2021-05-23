package com.skillbox.webshop.service;

import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getCurrentShop(String id) {
        return shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shop is not found"));
    }

    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public List<Item> saveItemToShop(String shopId, String itemId, String amount) {
        // это надо полностью переделать
        return new ArrayList<>();
    }

    public void deleteShop(String id) {
        if (shopRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Shop is not found");
        } else shopRepository.deleteById(id);
    }
}
