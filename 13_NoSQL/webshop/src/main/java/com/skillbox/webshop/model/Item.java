package com.skillbox.webshop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Schema (description = "Товар")
@Document (collection = "items")
public class Item {

    @Id
    @Schema (hidden = true)
    private String id;

    @Schema (description = "Название товара", example = "Хлебушек")
    private String name;

    @Schema (description = "Цена товара", example = "30")
    private Double price;

    @Schema (description = "Категория товара", example = "Продукты")
    private String category;

    @Schema (description = "Срок годности", example = "2021-06-01")
    private LocalDate bestBefore;

}
