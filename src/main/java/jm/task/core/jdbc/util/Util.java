package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public final static String URL = "jdbc:mysql://localhost:3306/preproject_db";
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "admin1234@";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connection is established!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USERNAME);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.SHOW_SQL, true);
        settings.put(Environment.FORMAT_SQL, true);
        settings.put(Environment.DEFAULT_SCHEMA, "preproject_db");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

        Configuration configuration = new Configuration();
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        try {
           sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }

        return sessionFactory;
    }
}
