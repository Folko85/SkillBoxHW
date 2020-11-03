package main.controller;

import main.dto.TaskMapper;
import main.dto.TaskModel;
import main.model.Task;
import main.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public List<TaskModel> getTasks() {
        return service.findAll().stream().map(TaskMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/tasks/{id}")           // а вдруг мы изменим фронт
    public TaskModel getTaskById(@PathVariable Integer id) {
        return TaskMapper.map(service.findById(id));
    }

    @PostMapping("/tasks")
    public TaskModel addTask(@Valid @RequestBody Task task) {
        return TaskMapper.map(service.save(task));
    }

    @PutMapping("/tasks")
    public TaskModel editTask(@Valid @RequestBody Task task) {
        return TaskMapper.map(service.save(task));
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @DeleteMapping("/tasks")
    public void deleteTasks() {
        service.deleteAll();
    }
}
