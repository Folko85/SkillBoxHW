package com.skillbox.webshop.service;

import com.skillbox.webshop.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    public List<Item> getAllItems() {
        return new ArrayList<>();
    }

    public List<Item> getItemsInShop( String shopId) {
        return new ArrayList<>();
    }

    public Item getOneItem() {
        return new Item();
    }

    public Item saveItem(Item item) {

        return item;
    }

    public void deleteItem(String id) {

    }
}
