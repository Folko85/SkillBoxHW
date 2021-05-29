package com.skillbox.webshop.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "shops")
public class Shop {

    @Id
    private String id;

    private String name;

    private String address;

                   //По условию задания количество товара не учитываем
                  // Эта аннотация делает список товаров - ссылками на документы коллекции items,
    @DBRef        // что является плохим тоном для MongoDB однако необходимо нам для тренировки навыков))
    private List<Item> items = new ArrayList<>();

}
