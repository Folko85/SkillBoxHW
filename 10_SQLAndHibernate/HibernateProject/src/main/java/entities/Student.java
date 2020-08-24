package entities;

import entities.notifications.Notification;
import entities.notifications.SendWork;
import entities.notifications.WriteComment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id.student")
    private List<Subscription> subscriptions;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<Course> courses;

    public Student() {
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void sendWork(String theme, Teacher teacher) { // чтоб не усложнять схему предположим, что работа адресована
        // конкретному учителю. Проверять, его ли это учитель не будем
        SendWork notification = new SendWork(teacher, theme, "Уведомление о присланной работе", this);
        teacher.getNotifications().add(notification);
    }

    public void writeComment(String text, Teacher teacher) {
        WriteComment notification = new WriteComment(teacher, text, "Уведомление о комментарии", this);
        teacher.getNotifications().add(notification);
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

}
