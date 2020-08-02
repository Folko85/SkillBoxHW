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
    static List<Course> courses;

    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");     // возможно, это неверное решение,
        hibernateLogger.setLevel(Level.WARNING);                           // но эти бесконечные сообщения просто мешают
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        courses = session.createQuery("from Course").list();  //в принципе в этой строчке вся наша программа
        int courseId = new Random().nextInt(courses.size());        // выведем случайный курс по id
        System.out.println(getCourseInfo(courseId));

        session.close();
        sessionFactory.close();

    }

    public static String getCourseInfo(int id) {         // ТЗ предполагает выводить информацию не о каждом, а о любом курсе, так что влепим сюда этот метод
        if (id <= 0 || id > courses.size()) return "Курс не найден";
        return courses.stream().filter(c -> c.getId() == id).findFirst().orElse(null).toString();
    }
}
