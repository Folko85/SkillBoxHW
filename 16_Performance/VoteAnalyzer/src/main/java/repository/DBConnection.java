package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "user";
    private static String dbPass = "password";

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                            "?user=" + dbUser + "&password=" + dbPass);
            connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
            connection.createStatement().execute("CREATE TABLE voter_count(" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "birthDate DATE NOT NULL, " +
                    "PRIMARY KEY(id))");
        }
        return connection;
    }

    public static void writeVoters(StringBuilder sql) throws SQLException {
        DBConnection.getConnection();
        DBConnection.getConnection().createStatement()
                .execute(sql.toString());

    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, COUNT(*) AS q FROM voter_count GROUP BY name ,birthDate HAVING q > 1;";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("q"));
        }
    }

    public static void addIndex() throws SQLException {
        long time = System.currentTimeMillis();
        String sql = "CREATE INDEX name_birthday ON voter_count(name, birthDate);";
        DBConnection.getConnection().createStatement().execute(sql);
        time = System.currentTimeMillis() - time;
        LocalTime result = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime();
        System.out.println("Создание индекса заняло: " + result.getMinute()  + " минут и " + result.getSecond() + " секунд\n");
    }
}
