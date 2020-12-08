package main.mapper;

import main.dto.TaskModel;
import main.model.Task;
import main.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TaskMapper {

    private TaskMapper() {
    }

    public static TaskModel map(Task item) {
        return new TaskModel()
                .setId(item.getId())
                .setTitle(item.getTitle());
    }

    public static Task reverseMap(TaskModel item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) auth.getPrincipal();
        return new Task()
                .setId(item.getId())
                .setTitle(item.getTitle())
                .setUser(userDetail);
    }

}
