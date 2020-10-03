package main.controller;

import main.model.Task;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TaskControllerTest extends AbstractIntegrationTest {

    @Test
    public void testGetTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/tasks")
                .content(mapper.writeValueAsString(new Task("titleText")))   //постим задачу
                .contentType(MediaType.APPLICATION_JSON)                          //тип на входе json
                .accept(MediaType.APPLICATION_JSON))                              //вернуть должно json
                .andExpect(MockMvcResultMatchers.status().isOk())                      //статус 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());   //у возвращённых объектов есть id
    }

    @Test
    public void testEditTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/tasks")
                .content(mapper.writeValueAsString(new Task("newTitleText")))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("newTitleText")); //проверяем, что сменился
    }

    @Test
    public void testDeleteTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/tasks/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}