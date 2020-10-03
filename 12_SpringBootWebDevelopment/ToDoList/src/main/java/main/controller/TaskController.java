package main.controller;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return TaskRepository.getAllTasks();
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        return TaskRepository.addTask(task);
    }

    @PutMapping("/tasks")
    public Task editTask(@RequestBody Task task) {
        return TaskRepository.editTask(task);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Integer id) {
        TaskRepository.deleteTask(id);
        return (TaskRepository.getTaskById(id) == null) ? new ResponseEntity<>(null, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/tasks")
    public ResponseEntity<HttpStatus> deleteTasks() {
        TaskRepository.deleteAllTasks();
        return (TaskRepository.getAllTasks().isEmpty()) ? new ResponseEntity<>(null, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
