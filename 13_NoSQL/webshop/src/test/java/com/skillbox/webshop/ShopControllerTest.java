package com.skillbox.webshop;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import com.skillbox.webshop.mapper.ShopMapper;
import com.skillbox.webshop.model.Item;
import com.skillbox.webshop.model.Shop;
import com.skillbox.webshop.repository.ItemRepository;
import com.skillbox.webshop.repository.ShopRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;

@Testcontainers
@SpringBootTest(classes = {WebshopApplication.class, ShopControllerTest.SpringMongoConfig.class})
class ShopControllerTest extends ApplicationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.1.1"));

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

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
    public void testGetAllShops() throws Exception {
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        shopRepository.save(shop);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/shop/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Arrays.asList(ShopMapper.map(shop)))));
    }

    @Test
    public void testCurrentShop() throws Exception {
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        String id = shopRepository.save(shop).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/shop/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(ShopMapper.map(shop))));
    }

    @Test
    public void testCurrentShopFail() throws Exception {
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        String id = shopRepository.save(shop).getId();
        shopRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/shop/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/shop/create")
                .content(mapper.writeValueAsString(new Shop().setName("Товар").setAddress("Где-то там")))   //постим задачу
                .contentType(MediaType.APPLICATION_JSON)                          //тип на входе json
                .accept(MediaType.APPLICATION_JSON))                              //вернуть должно json
                .andExpect(MockMvcResultMatchers.status().isOk())                      //статус 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());   //у возвращённых объектов есть id
    }

    @Test
    public void testExposeItem() throws Exception {
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        String shopId = shopRepository.save(shop).getId();
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String itemId = itemRepository.save(item).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/shop/expose")
                .param("shopId", shopId)
                .param("itemId", itemId))
                .andExpect(MockMvcResultMatchers.status().isOk());                 //статус 200
    }

    @Test
    public void testExposeItemFail() throws Exception {
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        String shopId = shopRepository.save(shop).getId();
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String itemId = itemRepository.save(item).getId();
        itemRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/shop/expose")
                .param("shopId", shopId)
                .param("itemId", itemId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());                 //статус 200
    }

    @Test
    public void testUpdateShopInfo() throws Exception {
        String id = shopRepository.save(new Shop().setName("Имя").setAddress("Адрес")).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .put("/shop/update")
                .content(mapper.writeValueAsString(new Shop().setName("НовоеИмя").setAddress("Адрес").setId(id)))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("НовоеИмя")); //проверяем, что сменился
    }

    @Test
    public void testUpdateShopInfoFail() throws Exception {
        String id = shopRepository.save(new Shop().setName("Имя").setAddress("Адрес")).getId();
        shopRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .put("/shop/update")
                .content(mapper.writeValueAsString(new Shop().setName("НовоеИмя").setAddress("Адрес").setId(id)))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testRemoveShop() throws Exception {
        Shop shop = new Shop().setName("Товар").setAddress("Адрес");
        String id = shopRepository.save(shop).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/shop/delete/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRemoveShopFail() throws Exception {
        Shop shop = new Shop().setName("Товар").setAddress("Адрес");
        String id = shopRepository.save(shop).getId();
        shopRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/shop/delete/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteItemFromShop() throws Exception {
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String itemId = itemRepository.save(item).getId();
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        shop.getItems().add(item);
        String shopId = shopRepository.save(shop).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/shop/delete/item")
                .param("shopId", shopId)
                .param("itemId", itemId))
                .andExpect(MockMvcResultMatchers.status().isOk());                 //статус 200
    }

    @Test
    public void testDeleteItemFromShopFail() throws Exception {
        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
        String itemId = itemRepository.save(item).getId();
        Shop shop = new Shop().setName("Имя").setAddress("Адрес");
        String shopId = shopRepository.save(shop).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/shop/delete/item")
                .param("shopId", shopId)
                .param("itemId", itemId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());                 //статус 200
    }

    @Configuration
    public static class SpringMongoConfig {

        public String getDatabaseName() {
            return "test";
        }

        @Bean
        public MongoClient mongoClient() {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(mongoDBContainer.getReplicaSetUrl()))
                    .build();
            MongoDriverInformation information = MongoDriverInformation.builder().build();
            return new MongoClientImpl(settings, information);
        }

        @Bean
        public SimpleMongoClientDatabaseFactory mongoDbFactory() {
            return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
        }

        @Bean
        public MongoTemplate mongoTemplate() {
            return new MongoTemplate(mongoDbFactory());
        }
    }
}
