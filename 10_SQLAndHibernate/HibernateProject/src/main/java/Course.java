import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('DESIGN', 'PROGRAMMING', 'MARKETING', 'MANAGEMENT', 'BUSINESS')")
    private CourseType type;

    private String description;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "teacher_id")
    private Teacher teacher;

    @Column(name = "students_count")
    private Integer studentsCount;   // в таблице есть курсы с незаполненными полями, так что меняем на Integer

    private Integer price;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Subscriptions",
    joinColumns = {@JoinColumn (name = "course_id")},
    inverseJoinColumns = {@JoinColumn (name = "student_id")})
    private List<Student> students;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true)
    private List<Subscription> subscriptions;

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setPricePerHour(Float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public CourseType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public Integer getPrice() {
        return price;
    }

    public Float getPricePerHour() {
        return pricePerHour;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getName() + ". Преподаватель: " + this.getTeacher().getName() + ". Студентов: " + this.getStudents().size();
    }
}
