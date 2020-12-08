package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table (name = "task_tab")
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
    private Long id;

    @NotBlank
    private String title;

    @Column(name = "date_time_of_create")
    private LocalDateTime dateTimeOfCreate;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public Task setId(Long id) {
        this.id = id;
        return this;
    }

    public Task setUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDateTime getDateTimeOfCreate() {
        return dateTimeOfCreate;
    }

    public void setDateTimeOfCreate(LocalDateTime dateTimeOfCreate) {
        this.dateTimeOfCreate = dateTimeOfCreate;
    }
}
