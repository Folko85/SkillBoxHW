package com.skillbox.webshop.service;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    public List<Shop> getAllShops() {
        return new ArrayList<>();
    }

    public Shop getCurrentShop(String id) {
        return new Shop();
    }

    public Shop saveShop(Shop shop) {
        return new Shop();
    }

    public List<Item> saveItemToShop(String shopId, String itemId, String amount) {
        return new ArrayList<>();
    }

    public void deleteShop(String id) {
    }
}
