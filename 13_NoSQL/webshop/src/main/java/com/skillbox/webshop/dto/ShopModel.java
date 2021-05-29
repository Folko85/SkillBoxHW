package com.skillbox.webshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
@Schema (description = "Магазин")
public class ShopModel {

    @Id
    @Schema (hidden = true)
    private String id;

    @Schema (description = "Название магазина", example = "Товары у дома")
    private String name;

    @Schema (description = "Адрес магазина", example = "ул Пушкина, дом 15")
    private String address;

}