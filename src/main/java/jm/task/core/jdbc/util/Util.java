package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public final static String URL = "jdbc:mysql://localhost::3306/preproject_db";
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "admin1234@";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connection is established!");
            }

        } catch (SQLException e) {
            System.out.println("Can't load driver class!");
        }

        return connection;
    }
}
