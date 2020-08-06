import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Subscriptions")
public class Subscription {

    @EmbeddedId
    private Key id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;


    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public static class Key implements Serializable {

        @Column(name = "student_id")
        private Integer studentId;

        @Column(name = "course_id")
        private Integer courseId;

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public Integer getStudentId() {
            return studentId;
        }

        public Integer getCourseId() {
            return courseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (!studentId.equals(key.studentId)) return false;
            return courseId.equals(key.courseId);
        }

        @Override
        public int hashCode() {
            int result = studentId.hashCode();
            result = 31 * result + courseId.hashCode();
            return result;
        }

    }
}
