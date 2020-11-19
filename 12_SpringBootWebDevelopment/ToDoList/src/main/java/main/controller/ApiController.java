package main.controller;

import main.dto.TaskMapper;
import main.dto.TaskModel;
import main.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    private final TaskService service;

    public ApiController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/api/tasks")
    public List<TaskModel> getTasks() {
        return service.findAll().stream().map(TaskMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/api/tasks/{id}")           // а вдруг мы изменим фронт
    public TaskModel getTaskById(@PathVariable Integer id) {
        return TaskMapper.map(service.findById(id));
    }

    @PostMapping("/api/tasks")
    public TaskModel addTask(@Valid @RequestBody TaskModel task) {
        return TaskMapper.map(service.save(TaskMapper.reverseMap(task)));
    }

    @PutMapping("/api/tasks")
    public TaskModel editTask(@Valid @RequestBody TaskModel task) {
        return TaskMapper.map(service.save(TaskMapper.reverseMap(task)));
    }

    @DeleteMapping("/api/tasks/{id}")
    public void deleteTaskById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @DeleteMapping("/api/tasks")
    public void deleteTasks() {
        service.deleteAll();
    }
}