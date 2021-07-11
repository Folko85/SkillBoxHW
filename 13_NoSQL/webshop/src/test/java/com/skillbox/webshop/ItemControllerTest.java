package com.skillbox.webshop;

import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ItemRepository;
import com.skillbox.webshop.repository.ShopRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;


@SpringBootTest(classes = {WebshopApplication.class, ApplicationTest.SpringMongoConfig.class})
class ItemControllerTest extends ApplicationTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ShopRepository shopRepository;

    @BeforeAll
    static void setUpAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void tearDownAll() {
        if (!mongoDBContainer.isShouldBeReused()) {
            mongoDBContainer.stop();
        }
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @Test
    public void testGetAllItems() throws Exception {
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        itemRepository.save(item);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/item/list/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Arrays.asList(item))));
    }

    @Test
    public void testGetItemById() throws Exception {
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String id = itemRepository.save(item).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/item/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(item)));
    }

    @Test
    public void testGetItemByIdFail() throws Exception {
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String id = itemRepository.save(item).getId();
        itemRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/item/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/item/create")
                .content(mapper.writeValueAsString(new Item().setName("Товар").setPrice(100D)))   //постим задачу
                .contentType(MediaType.APPLICATION_JSON)                          //тип на входе json
                .accept(MediaType.APPLICATION_JSON))                              //вернуть должно json
                .andExpect(MockMvcResultMatchers.status().isOk())                      //статус 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());   //у возвращённых объектов есть id
    }

    @Test
    public void testUpdateItem() throws Exception {
        String id = itemRepository.save(new Item().setName("Имя").setCategory("Категория").setPrice(100D)).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .put("/item/update")
                .content(mapper.writeValueAsString(new Item().setName("Товар").setPrice(100D).setId(id)))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Товар")); //проверяем, что сменился
    }

    @Test
    public void testUpdateItemFail() throws Exception {
        String id = itemRepository.save(new Item().setName("Имя").setCategory("Категория").setPrice(100D)).getId();
        itemRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .put("/item/update")
                .content(mapper.writeValueAsString(new Item().setName("Товар").setPrice(100D).setId(id)))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteItem() throws Exception {
        Item item = new Item().setName("Товар").setPrice(100D);
        String id = itemRepository.save(item).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/item/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteItemFail() throws Exception {
        Item item = new Item().setName("Товар").setPrice(100D);
        String id = itemRepository.save(item).getId();
        itemRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/item/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetItemsInShop() throws Exception {
        Item item = new Item().setName("Товар").setCategory("Категория").setPrice(100D);
        itemRepository.save(item);
        Shop shop = new Shop().setName("Продукты");
        shop.getItems().add(item);
        String id = shopRepository.save(shop).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/item/list/{shopId}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Arrays.asList(item))));
    }

    @Test
    public void testGetItemsInShopFail() throws Exception {
        Shop shop = new Shop().setName("Продукты");
        String id = shopRepository.save(shop).getId();
        shopRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/item/list/{shopId}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
