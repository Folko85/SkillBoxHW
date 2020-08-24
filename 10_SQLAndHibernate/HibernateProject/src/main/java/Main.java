import entities.Course;
import entities.Student;
import entities.Teacher;
import entities.notifications.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.WARNING);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
             Session session = sessionFactory.openSession()) {
            List<Teacher> teacherList = session.createQuery("from Teacher").list();
            List<Course> courseList = session.createQuery("from Course").list();
            List<Student> studentList = session.createQuery("from Student").list();
            Teacher testTeacher = teacherList.get(new Random().nextInt(teacherList.size()));  // возьмём случайного учителя из списка.
            System.out.println(testTeacher + ": " + testTeacher.getNotifications());
            Course testCourse = courseList.get(new Random().nextInt(courseList.size()));
            testCourse.hairTeacher(testTeacher);  // добавим случайному учителю случайный курс
            Student testStudent = studentList.get(new Random().nextInt(studentList.size()));
            testStudent.sendWork("Влияние уведомлений на безопасность учебного процесса", testTeacher);
            testStudent.writeComment("Ответьте на мой ответ??? Я ведь жду!!", testTeacher);
            System.out.println(testTeacher + ": " + testTeacher.getNotifications()); // и проверим, уведомили ли его.
            List<Notification> notificationList = testTeacher.getNotifications();
            session.save(testTeacher);
            session.save(testCourse);
            session.save(testStudent);  // раз уж сохраняем, то не только уведомления, но и действия.
            notificationList.forEach(n -> session.save(n)); // как ни странно, это работает.
            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
