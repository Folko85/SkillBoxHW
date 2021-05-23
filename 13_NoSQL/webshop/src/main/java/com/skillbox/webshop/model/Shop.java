package com.skillbox.webshop.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.skillbox.webshop.utils.ObjectIdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document (collection = "shops")
@Data
@Schema (description = "Магазин")
public class Shop {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Schema (hidden = true)
    private ObjectId id;

    @Schema (description = "Название магазина")
    private String name;

    @Schema (description = "Адрес магазина")
    private String address;

    private Map<Item, Integer> items;

}
