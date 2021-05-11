package com.skillbox.webshop.repository;

import com.skillbox.webshop.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {
}
