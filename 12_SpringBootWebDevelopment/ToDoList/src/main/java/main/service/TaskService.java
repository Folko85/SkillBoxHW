package main.service;


import main.exception.EntityNotFoundException;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Задание отсутствует"));
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Integer id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Задание не существует");
        } else taskRepository.deleteById(id);
    }

    public void deleteAll() {
        ArrayList<Task> tasks = (ArrayList<Task>) taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("Задания отсутствуют");
        } else taskRepository.deleteAll();
    }
}
