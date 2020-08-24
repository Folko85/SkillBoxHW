package entities;

import entities.notifications.AddCourse;
import entities.notifications.Notification;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Contracts",
            joinColumns = {@JoinColumn(name = "course_id", columnDefinition = "INT(11) UNSIGNED")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id", columnDefinition = "INT(11) UNSIGNED")})
    private List<Teacher> teachers;

    @Column(name = "students_count")
    private Integer studentsCount;

    private Integer price;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Subscriptions",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<Student> students;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id.course")
    private List<Subscription> subscriptions;

    public List<Student> getStudents() {
        return students;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public Course() {
    }

    public Course(String name, Integer duration, String description, Integer price) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.price = price;
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

    public void hairTeacher(Teacher teacher) {
        if (teachers == null) {
            teachers = new ArrayList<>();
        }
        teachers.add(teacher);
        AddCourse addCourse = new AddCourse(teacher, "Вам добавлен курс" + this.getName(), "Уведомление о новом курсе", this);
        teacher.getNotifications().add(addCourse);
        if (!teacher.getCourses().contains(this)) { // во избежание лишней рекурсии.
            teacher.addCourse(this);
        }
    }

    public void fairTeacher(Teacher teacher) {
        if (teachers.contains(teacher)) {
            teachers.remove(teacher);
            if (teacher.getCourses().contains(this)) {
                teacher.removeCourse(this);
            }
        }
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

    public List<Teacher> getTeachers() {
        return teachers;
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
        return this.getName() + ". Студентов: " + this.getStudents().size();
    }
}

enum CourseType {

    DESIGN,
    PROGRAMMING,
    MARKETING,
    MANAGEMENT,
    BUSINESS
}
