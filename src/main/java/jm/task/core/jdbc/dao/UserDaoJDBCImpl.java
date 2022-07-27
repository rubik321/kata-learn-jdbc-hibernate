package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(16) NOT NULL," +
            "lastName VARCHAR(32) NOT NULL," +
            "age TINYINT UNSIGNED);";
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users;";
    public static final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?,  ?);";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";

    private Connection connection;

    public UserDaoJDBCImpl() {

    }

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USERS_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(DROP_USERS_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(REMOVE_USER)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(CLEAN_USERS_TABLE)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
