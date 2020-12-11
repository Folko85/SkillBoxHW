package main.controller;

import main.mapper.TaskMapper;
import main.model.Task;
import main.repository.TaskRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

public class ApiControllerTest extends AbstractIntegrationTest {

    @Autowired
    private TaskRepository repository;                //в тестовом классе конструкторов нельзя
    private Task task;

    @Before              //обычное Before выполняется перед каждым тестом
    public void setUpTest(){
      //  SecurityContextHolder.getContext().setAuthentication(null);
        task = new Task("taskText");
        repository.save(task);
    }

    @After                      //поэтому после каждого теста удаляем все таски
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    @WithMockUser
    public void testGetTasksSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Arrays.asList(TaskMapper.map(task)))));
    }

    @Test
    public void testGetTaskByIdSuccess() throws Exception {
        long id = task.getId();   // абракадабра какая-то
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/tasks/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(TaskMapper.map(task))));
    }

    @Test
    public void testGetTaskByIdFailure() throws Exception {
        repository.deleteAll();  // для этого теста нужны особые условия
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/tasks/{id}", 100)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Task is not exist"));
    }

    @Test
    public void testAddTaskSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/tasks")
                .content(mapper.writeValueAsString(new Task("titleText")))   //постим задачу
                .contentType(MediaType.APPLICATION_JSON)                          //тип на входе json
                .accept(MediaType.APPLICATION_JSON))                              //вернуть должно json
                .andExpect(MockMvcResultMatchers.status().isOk())                      //статус 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());   //у возвращённых объектов есть id
    }

    @Test
    public void testAddTaskFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/tasks")
                .content(mapper.writeValueAsString(new Task("")))   //постим задачу
                .contentType(MediaType.APPLICATION_JSON)                          //тип на входе json
                .accept(MediaType.APPLICATION_JSON))                              //вернуть должно json
                .andExpect(MockMvcResultMatchers.status().isBadRequest())                      //статус 400
                .andExpect(MockMvcResultMatchers.content().string("Field cannot being empty"));   //у возвращённых объектов есть id
    }

    @Test
    public void testEditTaskSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/tasks")
                .content(mapper.writeValueAsString(new Task("newTitleText")))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("newTitleText")); //проверяем, что сменился
    }

    @Test
    public void testEditTaskFailure() throws Exception {   // в нашей реализации сделать так не выйдет, ну и фиг с ним
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/tasks")
                .content(mapper.writeValueAsString(new Task("")))  //меняем текст на этот
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Field cannot being empty")); //проверяем, что сменился
    }

    @Test
    public void testDeleteTaskByIdSuccess() throws Exception {
        long id = task.getId();   // удаляем последний элемент. Хз, почему эти тесты цепляются друг за дружку
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/tasks/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTaskByIdFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/tasks/{id}", 100))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Task is not exist"));
    }

    @Test
    public void testDeleteTasksSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTasksFailure() throws Exception {
        repository.deleteAll();   // тоже особые условия
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/tasks"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Task is not exist"));
    }
}