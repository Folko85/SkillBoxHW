package entities.notifications;

import entities.Teacher;

import javax.persistence.*;

/**
 * Мы применим стратегию наследования "Одна таблица на класс с соединениями", Иначе "JOINED"
 * как наиболее гибкую и простую для изменения. С учётом того, что нативные запросы никто делать не будет.
 * А вот наличие полиморфных ассоциаций похоже предполагается (ведь уведомления по идее могут приходить
 * разным сущностям и даже уже приходят от разных сущностей). При этом стратегия единой таблицы отличается
 * сложностью сопровождения (наличие многочисленных null-полей), а также нарушением нормализации.
 * Ну и на вид JOINED-стратегия выглядит наиболее понятной и близкой к ООП.
 **/
@Entity
@Table(name = "Notifications")
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)                   // каждое уведомление отправляется конкретному учителю.
    @JoinColumn(name = "teacher_id", columnDefinition = "INT(11) UNSIGNED")                               //Но у учителя может быть много уведомлений
    private Teacher teacher;

    @Column(name = "notification_text")
    private String notificationText;

    @Column(name = "notification_title")
    private String notificationTitle;

    public Notification() {
    }

    public Notification(Teacher teacher, String text, String title) {
        this.teacher = teacher;
        this.notificationText = text;
        this.notificationTitle = title;
    }

    @Override
    public String toString(){
        return this.notificationTitle;
    }

}
