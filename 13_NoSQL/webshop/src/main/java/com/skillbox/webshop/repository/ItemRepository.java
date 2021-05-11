package com.skillbox.webshop.repository;

import com.skillbox.webshop.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
