package com.skillbox.webshop;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import org.junit.jupiter.api.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
@SpringBootTest(classes = {WebshopApplication.class, ItemControllerTest.SpringMongoConfig.class })
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
class ItemControllerTest extends ApplicationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

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
//        itemRepository.deleteAll();
    }

    @Test
    public void testGetAllItems() throws Exception {
//        Item item = new Item().setName("Имя").setCategory("Категория").setPrice(100D);
//        itemRepository.save(item);
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/item/list/all")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Arrays.asList(item))));
    }


    @Configuration
    public static class SpringMongoConfig {

        public String getDatabaseName() {
            return "test";
        }

        @Bean
        public MongoClient mongoClient() {
            MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(mongoDBContainer.getReplicaSetUrl())).build();
            MongoDriverInformation information = MongoDriverInformation.builder().build();
            MongoClient client = new MongoClientImpl(settings, information);
            return client;
        }

        @Bean
        public SimpleMongoClientDatabaseFactory mongoDbFactory() throws Exception {
            return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
        }

        @Bean
        public MongoTemplate mongoTemplate() throws Exception {
            MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
            return mongoTemplate;
        }
    }

}
