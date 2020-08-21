package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LinkedPurchaseList")
public class Purchase {
    // в итоге класс не будет отличаться от Subscription, поскольку все поля таблицы PurchaseList
    // дублируются в других классах и если их оставить здесь, то при изменении данных в одной из таблиц
    // возникнет несогласованность между ними.

    @EmbeddedId
    private PurchaseKey key;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;  // точно, private

    public Purchase() {
    }

    public Purchase(Student student, Course course, LocalDateTime subscriptionDate) {
        this.key = new PurchaseKey(student, course);
        this.subscriptionDate = subscriptionDate;
    }

    public PurchaseKey getId() {
        return key;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setId(PurchaseKey id) {
        this.key = id;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (!Objects.equals(key, purchase.key)) return false;
        return Objects.equals(subscriptionDate, purchase.subscriptionDate);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (subscriptionDate != null ? subscriptionDate.hashCode() : 0);
        return result;
    }

    @Embeddable
    private static class PurchaseKey implements Serializable {

        @ManyToOne
        @JoinColumn(name = "course_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")  //работает только при создании,
        private Course course;                                                           // так что пришлось пересоздавать таблицу

        @ManyToOne
        @JoinColumn(name = "student_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
        private Student student;

        public PurchaseKey() {
        }

        public PurchaseKey(Student student, Course course) {
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

            Purchase.PurchaseKey key = (Purchase.PurchaseKey) o;

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
