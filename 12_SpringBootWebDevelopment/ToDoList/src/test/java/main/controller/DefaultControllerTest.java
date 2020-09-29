package main.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DefaultControllerTest extends AbstractIntegrationTest {

    @Test
    public void shouldReturnSomething() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}