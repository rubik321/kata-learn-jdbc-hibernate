package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String table = "users";

    private Connection connection;

    public UserDaoJDBCImpl() {

    }

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() {
        executeStatement("CREATE TABLE IF NOT EXISTS %s (".formatted(table) +
                "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(16) NOT NULL," +
                "lastName VARCHAR(32) NOT NULL," +
                "age TINYINT UNSIGNED);");
    }

    public void dropUsersTable() {
        executeStatement("DROP TABLE IF EXISTS %s;".formatted(table));
    }

    public void saveUser(String name, String lastName, byte age) {
        executeStatement(
                "INSERT INTO %s (name, lastName, age) VALUES ('%s', '%s', %d);".formatted(table, name, lastName, age));
    }

    public void removeUserById(long id) {
        executeStatement("DELETE FROM %s WHERE id = %s;".formatted(table, id));
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        PreparedStatement ps = getPreparedStatement("SELECT * FROM %s".formatted(table));

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getByte(4));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(ps);
        }

        return users;
    }

    public void cleanUsersTable() {
        executeStatement("TRUNCATE TABLE %s".formatted(table));
    }

    private void executeStatement(String sql) {
        PreparedStatement ps = getPreparedStatement(sql);
        executePreparedStatement(ps);
        closePreparedStatement(ps);
    }

    private PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    private void executePreparedStatement(PreparedStatement statement) {
        try {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
