package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Subscriptions")
public class Subscription {

    @EmbeddedId
    private Key id;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public void setId(Key id) {
        this.id = id;
    }

    public Subscription() {
    }

    public Subscription(Student student, Course course, LocalDateTime subscriptionDate) {
        this.id = new Key(student, course);
        this.subscriptionDate = subscriptionDate;
    }

    public Key getId() {
        return id;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    @Embeddable
    public static class Key implements Serializable {

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        @ManyToOne
        @JoinColumn(name = "course_id")
        private Course course;

        public Key(Student student, Course course) {
            this.student = student;
            this.course = course;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public Student getStudent() {
            return student;
        }

        public Course getCourse() {
            return course;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (!student.equals(key.student)) return false;
            return course.equals(key.course);
        }

        @Override
        public int hashCode() {
            int result = student.hashCode();
            result = 31 * result + course.hashCode();
            return result;
        }

    }
}
