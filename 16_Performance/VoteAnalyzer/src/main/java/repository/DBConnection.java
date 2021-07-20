package repository;

import java.sql.*;

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
                    "name TINYTEXT NOT NULL, " +
                    "birthDate DATE NOT NULL, " +
                    "PRIMARY KEY(id))");
        }
        return connection;
    }

    public static void writeVoters(StringBuilder sql) throws SQLException {
        Connection connection = DBConnection.getConnection();
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
}
