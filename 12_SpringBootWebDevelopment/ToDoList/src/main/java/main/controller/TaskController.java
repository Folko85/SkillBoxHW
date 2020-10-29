package main.controller;

import main.exception.EmptyFieldException;
import main.exception.EntityNotFoundException;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List<Task>) repository.findAll();
    }

    @GetMapping("/tasks/{id}")           // а вдруг мы изменим фронт
    public Task getTaskById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Задание отсутствует"));
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        if (task.getTitle().isEmpty()) {
            throw new EmptyFieldException("Нечего добавлять");
        } else return repository.save(task);
    }

    @PutMapping("/tasks")
    public Task editTask(@RequestBody Task task) {
        if (task.getTitle().isEmpty()) {
            throw new EmptyFieldException("Задание не может быть пустым");
        } else return repository.save(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(@PathVariable Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Задание не существует");
        } else repository.deleteById(id);
    }

    @DeleteMapping("/tasks")
    public void deleteTasks() {
        ArrayList<Task> tasks = (ArrayList<Task>) repository.findAll();
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("Задания отсутствуют");
        } else repository.deleteAll();
    }
}
