package main.controller;

import main.dto.TaskMapper;
import main.dto.TaskModel;
import main.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String start(Model model) {
        List<TaskModel> tasks = service.findAll().stream().map(TaskMapper::map).collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        List<TaskModel> tasks = service.findAll().stream().map(TaskMapper::map).collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "body";
    }

    @PostMapping("/tasks")
    public String addTask(@Valid @RequestBody TaskModel task, Model model) {
        service.save(TaskMapper.reverseMap(task));
        return getTasks(model);
    }

    @PutMapping("/tasks")
    public String editTask(@Valid @RequestBody TaskModel task, Model model) {
        service.save(TaskMapper.reverseMap(task));
        return getTasks(model);
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTaskById(@PathVariable Integer id, Model model) {
        service.deleteById(id);
        return getTasks(model);
    }

    @DeleteMapping("/tasks")
    public String deleteTasks(Model model) {
        service.deleteAll();
        return getTasks(model);
    }
}