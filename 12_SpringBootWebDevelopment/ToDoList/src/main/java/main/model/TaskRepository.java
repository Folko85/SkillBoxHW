package main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {

    private TaskRepository() {
    }

    private static int currentId = 1;
    private static final ConcurrentHashMap<Integer, Task> allTasks = new ConcurrentHashMap<>();

    public static List<Task> getAllTasks() {
        return new ArrayList<>(allTasks.values());
    }

    public static Task addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        allTasks.put(id, task);
        return task;
    }

    public static Task getTaskById(int id) {
        return allTasks.getOrDefault(id, null);
    }

    public static void deleteTask(int id) {
        allTasks.remove(id);
    }

    public static void deleteAllTasks() {
        allTasks.clear();
    }

    public static Task editTask(Task task) {
        int id = task.getId();
        allTasks.put(id, task);
        return task;
    }
}
