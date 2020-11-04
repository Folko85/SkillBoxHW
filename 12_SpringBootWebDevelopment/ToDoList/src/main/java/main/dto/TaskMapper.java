package main.dto;

import main.model.Task;

public class TaskMapper {

    private TaskMapper() {
    }

    public static TaskModel map(Task item) {
        return new TaskModel()
                .setId(item.getId())
                .setTitle(item.getTitle());
    }

    public static Task reverseMap(TaskModel item) {
        return new Task()
                .setId(item.getId())
                .setTitle(item.getTitle());
    }

}
