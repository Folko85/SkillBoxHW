import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class);
    static final String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=Europe/Moscow";
    static final String user = "root";
    static final String pass = "ABC-def-123";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT Courses.name as course_name, \n" +
                     "month(MAX(Subscriptions.subscription_date)) - month(MIN(Subscriptions.subscription_date)) + 1 as months, \n" +
                     "COUNT(*) as subs_count FROM Subscriptions\n" +
                     "JOIN Courses ON Courses.id = Subscriptions.course_id\n" +
                     "GROUP BY course_name;")
        ) {
            System.out.println("Среднее количество подписчиков по курсам:");
            String specifiers = "%-40s %.2f%n";
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                int monthsCount = Integer.parseInt(resultSet.getString("months"));
                double subsCount = Double.parseDouble(resultSet.getString("subs_count"));
                logger.info(courseName + " - " + (int) subsCount + " подписок и " + monthsCount + " месяцев");
                System.out.printf(specifiers, courseName, subsCount / monthsCount);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}
