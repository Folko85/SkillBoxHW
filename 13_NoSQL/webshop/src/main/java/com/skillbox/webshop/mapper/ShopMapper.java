package com.skillbox.webshop.mapper;

import com.skillbox.webshop.dto.ShopModel;
import com.skillbox.webshop.model.Shop;

public class ShopMapper {

    public static ShopModel map(Shop shop) {
        return new ShopModel()
                .setId(shop.getId())
                .setName(shop.getName())
                .setAddress(shop.getAddress());
    }

    public static Shop reverseMap(ShopModel shopModel) {
        return new Shop()
                .setId(shopModel.getId())
                .setName(shopModel.getName())
                .setAddress(shopModel.getAddress());
    }
}
