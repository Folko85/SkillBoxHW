package com.skillbox.webshop.repository;

import com.skillbox.webshop.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    @Query ("{'price': ?0 }")
    Item findByPrice(Double price);


}

