import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchaseList")
public class Purchase {

    @EmbeddedId
    private PurchaseKey id;

    @Column(name = "student_name", insertable = false, updatable = false)
    private String studentName;

    @Column(name = "course_name", insertable = false, updatable = false)
    private String courseName;

    private Integer price;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getPrice() {
        return price;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public static class PurchaseKey implements Serializable {

        @Column(name = "student_name")
        private String studentName;

        @Column(name = "course_name")
        private String courseName;

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getStudentName() {
            return studentName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PurchaseKey key = (PurchaseKey) o;

            if (!studentName.equals(key.studentName)) return false;
            return courseName.equals(key.courseName);
        }

        @Override
        public int hashCode() {
            int result = studentName.hashCode();
            result = 31 * result + courseName.hashCode();
            return result;
        }

        public String getCourseName() {
            return courseName;
        }
    }
}
