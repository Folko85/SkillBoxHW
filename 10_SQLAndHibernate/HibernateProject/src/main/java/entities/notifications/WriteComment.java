package entities.notifications;

import entities.Student;
import entities.Teacher;

import javax.persistence.*;

@Entity
@Table (name = "WriteComment")
public class WriteComment extends Notification {

    @ManyToOne(fetch = FetchType.LAZY) // каждое уведомление отправлено определённым студентом.
    @JoinColumn(name = "student_id", columnDefinition = "INT(11) UNSIGNED")  // Как минимум до тех пор, пока мы так захотели.
    Student student;

    public WriteComment() {
    }

    public WriteComment(Teacher teacher, String text, String title, Student student) {
        super(teacher, text, title);
        this.student = student;
    }

}
