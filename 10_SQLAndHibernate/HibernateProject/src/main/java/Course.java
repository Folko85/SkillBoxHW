import javax.persistence.*;

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

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "students_count")
    private Integer studentsCount;   // в таблице есть курсы с незаполненными полями, так что меняем на Integer

    private Integer price;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

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

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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

    public Integer getTeacherId() {
        return teacherId;
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
        return this.getName() + ". Количество студентов: " + this.getStudentsCount();
    }
}
