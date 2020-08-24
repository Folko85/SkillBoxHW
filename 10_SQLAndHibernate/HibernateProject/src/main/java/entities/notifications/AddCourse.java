package entities.notifications;

import entities.Course;
import entities.Teacher;

import javax.persistence.*;

@Entity
@Table(name = "AddCourse")
public class AddCourse extends Notification {

    @ManyToOne(fetch = FetchType.LAZY) // каждое уведомление отправлено определённым курсом.
    @JoinColumn(name = "course_id", columnDefinition = "INT(11) UNSIGNED")  // Как минимум до тех пор, пока мы так захотели.
    Course course;

    public AddCourse() {
    }

    public AddCourse(Teacher teacher, String text, String title, Course course) {
        super(teacher, text, title);
        this.course = course;
    }
}