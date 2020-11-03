package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Task {

    public Task(String title) {
        this.title = title;
        this.dateTimeOfCreate = LocalDateTime.now();    // это поле будет скрыто от юзера, однако будет храниться в базе
    }

    public Task() {
        this.dateTimeOfCreate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "date_time_of_create")
    private LocalDateTime dateTimeOfCreate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTimeOfCreate() {
        return dateTimeOfCreate;
    }

    public void setDateTimeOfCreate(LocalDateTime dateTimeOfCreate) {
        this.dateTimeOfCreate = dateTimeOfCreate;
    }
}
