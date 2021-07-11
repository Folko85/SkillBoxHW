package com.skillbox.webshop.service;

import com.skillbox.webshop.dto.ShopModel;
import com.skillbox.webshop.exception.DuplicateEntityException;
import com.skillbox.webshop.exception.EntityNotFoundException;
import com.skillbox.webshop.mapper.ShopMapper;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ItemRepository;
import com.skillbox.webshop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;

    public ShopService(ShopRepository shopRepository, ItemRepository itemRepository) {
        this.shopRepository = shopRepository;
        this.itemRepository = itemRepository;
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
        Item existingItem = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        Shop existingShop = this.getCurrentShop(shopId);

        if (existingShop.getItems().contains(existingItem)) {
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
        Item existingItem = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        Shop existingShop = this.getCurrentShop(shopId);
        if (existingShop.getItems().contains(existingItem)) {
            existingShop.getItems().remove(existingItem);
            this.saveShop(existingShop);
        } else {
            throw new EntityNotFoundException("Такой товар не был выставлен");
        }
    }

    public String getStatisticsForShop(String shopId) {
        Shop currentShop = shopRepository.findById(shopId).orElseThrow(() -> new EntityNotFoundException("Магазин не найден"));
        int count = currentShop.getItems().size();
        if (count == 0) {
            return "В магазине нет товаров";
        }
        Double average = shopRepository.averagePriceOfItems(shopId).orElse(0D);
        Double maxPrice = shopRepository.findMostExpensive(shopId).orElse(0D);
        Item mostExpensiveItem = itemRepository.findByPrice(maxPrice);
        Double minPrice = shopRepository.findChipest(shopId).orElse(0D);
        Item cheapestItem = itemRepository.findByPrice(minPrice);
        long cheapItemCount = currentShop.getItems().stream().filter(item -> item.getPrice() < 100D).count();


        return "В магазине " + currentShop.getName() + " всего товаров: " + count + ";\n" +
                "Средняя цена товаров: " + average + ";\n" +
                "Самый дорогой товар: " + mostExpensiveItem.getName() + ";\n" +
                "Самый дешевый товар: " + cheapestItem.getName() + ";\n" +
                "Количество товаров дешевле 100 рублей: " + cheapItemCount + ";\n";
    }
}
