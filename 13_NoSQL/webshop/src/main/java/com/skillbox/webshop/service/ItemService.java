package com.skillbox.webshop.service;

import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ItemRepository;
import com.skillbox.webshop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    public ItemService(ItemRepository itemRepository, ShopRepository shopRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsInShop(String shopId) {
        Shop targetShop = shopRepository.findById(shopId).orElseThrow(() -> new EntityNotFoundException("Магазин не найден"));
        return targetShop.getItems();
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(String id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Товар не найден");
        } else itemRepository.deleteById(id);
    }
}
