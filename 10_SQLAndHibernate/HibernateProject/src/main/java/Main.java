import entities.Course;
import entities.Purchase;
import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.OFF);                  // возможно, это неверное решение,
        // но эти бесконечные сообщения просто мешают, а варнинги ещё и вводят в заблуждение.
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Purchase> purchaseList = session.createQuery("from Purchase").list(); // таблица в базе данных создаётся на более раннем этапе
            List<Course> courseList = session.createQuery("from Course").list();       // поэтому если даже таблица пуста, мы получим из неё лист
            List<Student> studentList = session.createQuery("from Student").list();
            NativeQuery query = session.createSQLQuery("SELECT student_name, course_name, subscription_date FROM PurchaseList");
            List<Object[]> preformPurchase = query.list();   // массив заготовок для заполнения таблицы
            for (Object[] preform : preformPurchase) {
                String studentName = preform[0].toString();
                Student student = null;
                for (Student existingStudent : studentList) {  // находим студента по имени
                    if (existingStudent.getName().equals(studentName)) {
                        student = existingStudent;
                    }
                }
                String courseName = preform[1].toString();
                Course course = null;
                for (Course existingCourse : courseList) {    // находим курс по названию
                    if (existingCourse.getName().equals(courseName)) {
                        course = existingCourse;
                    }
                }
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
                LocalDateTime subscriptionDate = LocalDateTime.parse(preform[2].toString(), inputFormat);
                Purchase purchase = new Purchase(student, course, subscriptionDate);
                if (!purchaseList.contains(purchase)) { // это чтоб не было ошибок, связанных с задвоениями
                    session.save(purchase);
                }
            }
            transaction.commit();
            List<Purchase> resultList = session.createQuery("from Purchase").list();
            System.out.println("Итого сущностей в таблице: " + resultList.size());
            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
