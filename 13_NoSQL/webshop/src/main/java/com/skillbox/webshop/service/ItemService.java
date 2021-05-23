package com.skillbox.webshop.service;

import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsInShop( String shopId) {
        //переделать, чтоб получало только для конкретного магазина
        return itemRepository.findAll();
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item is not found"));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(String id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Item is not found");
        } else itemRepository.deleteById(id);
    }
}
