package com.skillbox.webshop.repository;

import com.skillbox.webshop.model.Shop;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {

    String match = "{ $match: { _id: ObjectId('?0') } }";                          // фильтруем по id магазина
    String unwind = "{$unwind: '$items'}";                                          //разворачиваем список товаров
    String transform = "{$addFields: {object: {$objectToArray: '$$ROOT.items'}}}";        // преобразуем ссылку в объект
    String lookup = "{$lookup: {from: 'items', localField: 'object.1.v', foreignField: '_id', as: 'product'}}";  // добавляем поля объекта магазину
    String nextUnwind = " {$unwind: '$product'}";                                           // разворачиваем список товаров (т.к lookup отдаёт их списком)
    String average = "{ $group: { _id: '$name', average: { $avg: '$product.price' } } }";  // получаем среднее значение
    String maxPrice = "{ $group: { _id: '$name', maxprice: { $max: '$product.price' } } }";  // получаем максимальное значение
    String minPrice = "{ $group: { _id: '$name', minprice: { $min: '$product.price' } } }";  // минимальное значение

    @Aggregation(pipeline = {match, unwind, transform, lookup, nextUnwind, average})
    Optional<Double> averagePriceOfItems(String shopId);

    @Aggregation(pipeline = {match, unwind, transform, lookup, nextUnwind, maxPrice})
    Optional<Double> findMostExpensive(String shopId);

    @Aggregation (pipeline = {match, unwind, transform, lookup, nextUnwind, minPrice} )
    Optional<Double> findChipest(String shopId);

}
