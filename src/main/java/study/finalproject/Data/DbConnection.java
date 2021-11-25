package study.finalproject.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if (connection != null){
            return connection;
        } else{
            createConnection();
            return connection;
        }
    }
    private static void createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/url";
        String user = "root";
        String password = "1111";
        connection = DriverManager.getConnection(url, user, password);
    }
}
