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
                    "`count` INT NOT NULL, " +
                    "PRIMARY KEY(id))");
        }
        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException {
        birthDay = birthDay.replace('.', '-');
        String sql =
            "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
        Connection connection = DBConnection.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sql);
        if (!rs.next()) {
            DBConnection.getConnection().createStatement()
                .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
                    name + "', '" + birthDay + "', 1)");
        } else {
            int id = rs.getInt("id");
            DBConnection.getConnection().createStatement()
                .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
        }
        rs.close();
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
