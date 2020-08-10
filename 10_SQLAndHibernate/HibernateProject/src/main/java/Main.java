import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");     // возможно, это неверное решение,
        hibernateLogger.setLevel(Level.WARNING);                           // но эти бесконечные сообщения просто мешают
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        try (Session session = sessionFactory.openSession()) {

            List<Course> courseList = session.createQuery("from Course").list();
            System.out.println(courseList.size());
            Transaction transaction = session.beginTransaction();
            Course course = new Course("Супер-курс по Hibernate", 1, "Учим хибернейту с нуля за 1 месяц. Гарантия полного понимания материала", 200_000);
            course.setType(CourseType.PROGRAMMING);
            Teacher teacher = new Teacher("Иванов Иван Петрович", 100_000, 34);
            teacher.addCourse(course);
            Student student = new Student("Вася Обломов", 18);
            student.setRegistrationDate(LocalDateTime.now());
            Subscription subscription = new Subscription(student, course, LocalDateTime.now());
            session.save(course);
            session.save(teacher);
            session.save(student);
            session.save(subscription);
            transaction.commit();
            courseList = session.createQuery("from Course").list();
            System.out.println(courseList.size());
            session.close();
            sessionFactory.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
