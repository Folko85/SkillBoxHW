package main.controller;

import main.exception.EmptyFieldException;
import main.exception.EntityNotFoundException;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {        //чтоб достигнуть отрицательного сценария, пусть каждый метод кидает экзепшены

    @GetMapping("/tasks")
    public List<Task> getTasks() throws EntityNotFoundException {
        if (TaskRepository.getAllTasks().isEmpty()) {
            throw new EntityNotFoundException("Задания отсутствуют");
        } else return TaskRepository.getAllTasks();
    }

    @GetMapping("/tasks/{id}")           // а вдруг мы изменим фронт
    public Task getTaskById(@PathVariable Integer id) throws EntityNotFoundException {
        if (TaskRepository.getTaskById(id) == null) {
            throw new EntityNotFoundException("Задание отсутствует");
        } else return TaskRepository.getTaskById(id);
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) throws EmptyFieldException {
        if (task.getTitle().isEmpty()) {
            throw new EmptyFieldException("Нечего добавлять");
        } else return TaskRepository.addTask(task);
    }

    @PutMapping("/tasks")
    public Task editTask(@RequestBody Task task) throws EmptyFieldException {
        if (task.getTitle().isEmpty()) {
            throw new EmptyFieldException("Задание не может быть пустым");
        } else return TaskRepository.editTask(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(@PathVariable Integer id) throws EntityNotFoundException {
        if (TaskRepository.getTaskById(id) == null) {
            throw new EntityNotFoundException("Задание не существует");
        } else TaskRepository.deleteTask(id);
    }

    @DeleteMapping("/tasks")
    public void deleteTasks() throws EntityNotFoundException {
        if (TaskRepository.getAllTasks().isEmpty()) {
            throw new EntityNotFoundException("Задания отсутствуют");
        } else TaskRepository.deleteAllTasks();
    }
}
