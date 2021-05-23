package com.skillbox.webshop.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.skillbox.webshop.utils.ObjectIdSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document (collection = "items")
@Data
@Schema (description = "Товар")
public class Item {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Schema (hidden = true)
    private ObjectId id;

    @Schema (description = "Название товара")
    private String name;

    @Schema (description = "Цена товара")
    private BigDecimal price;

    @Schema (description = "Категория товара")
    private String category;

    @Schema (description = "Срок годности")
    private LocalDate bestBefore;

}
