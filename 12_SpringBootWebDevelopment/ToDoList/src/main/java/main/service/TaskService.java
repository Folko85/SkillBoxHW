package main.service;


import main.exception.EntityNotFoundException;
import main.model.Task;
import main.model.User;
import main.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Задание отсутствует"));
    }

    public List<Task> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) auth.getPrincipal();
        return taskRepository.findAll().stream().filter(task -> task.getUser() != null)
                .filter(t -> t.getUser().getId().equals(userDetail.getId()))
                .collect(Collectors.toList());
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
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
