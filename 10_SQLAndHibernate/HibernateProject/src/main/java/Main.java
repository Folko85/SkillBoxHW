import entities.Course;
import entities.Purchase;
import entities.Student;
import entities.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static final String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=Europe/Moscow";
    static final String user = "root";
    static final String pass = "ABC-def-123";


    public static void main(String[] args) {
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.WARNING);

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();) {
            ClassLoader classLoader = Main.class.getClassLoader();
            BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("migrate.sql"))); // Нашли - применяем.
            String command;
            while ((command = reader.readLine()) != null) {
                statement.executeUpdate(command);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
             Session session = sessionFactory.openSession()) {
            // linkedPurchaseCheck(session);  // вынесем предыдущее ДЗ куда-нибудь подальше
            migrationCheck(session);

            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void migrationCheck(Session session) {
        Transaction transaction = session.beginTransaction();
        List<Course> courseList = session.createQuery("from Course").list();
        List<Teacher> teacherList = session.createQuery("from Teacher").list();
        printInfo(courseList, teacherList);   // Как и в изначальной базе, 12 учителей не привязаны к курсам. Давайте дадим им работу
        teacherList.stream().filter(teacher -> teacher.getCourses().size() == 0).forEach(t -> {
            t.addCourse(courseList.get(new Random().nextInt(46)));
            session.save(t);
        });
        printInfo(courseList, teacherList);
        transaction.commit();
    }

    public static void printInfo(List<Course> courseList, List<Teacher> teacherList) {
        courseList.forEach(c -> System.out.println(c.getName() + " - " + c.getTeachers()));
        teacherList.forEach(teacher -> System.out.println(teacher.getName() + " - " + teacher.getCourses()));
        long countUnHaired = teacherList.stream().filter(t -> t.getCourses().size() == 0).count();
        System.out.println("Учителей без курсов: " + countUnHaired);

    }

    public static void linkedPurchaseCheck(Session session) {
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
    }
}
