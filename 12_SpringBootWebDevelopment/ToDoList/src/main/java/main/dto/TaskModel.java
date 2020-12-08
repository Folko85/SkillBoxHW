package main.dto;

import javax.validation.constraints.NotBlank;

public class TaskModel {

    private Long id;

    @NotBlank
    private String title;

    public Long getId() {
        return id;
    }

    public TaskModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TaskModel setTitle(String title) {  //это (возвращение себя) для красивой записи в маппере
        this.title = title;
        return this;
    }
}
