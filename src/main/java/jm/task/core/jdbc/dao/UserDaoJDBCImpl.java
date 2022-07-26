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
        executeStatement(createStatement(), CREATE_USERS_TABLE);
    }

    public void dropUsersTable() {
        executeStatement(createStatement(), DROP_USERS_TABLE);
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement ps = getPreparedStatement(SAVE_USER);

        try {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executePreparedStatement(ps);
        closePreparedStatement(ps);
    }

    public void removeUserById(long id) {
        PreparedStatement ps = getPreparedStatement(REMOVE_USER);

        try {
            ps.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executePreparedStatement(ps);
        closePreparedStatement(ps);
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        PreparedStatement ps = getPreparedStatement(GET_ALL_USERS);

        try {
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
        } finally {
            closePreparedStatement(ps);
        }

        return users;
    }

    public void cleanUsersTable() {
        executeStatement(createStatement(), CLEAN_USERS_TABLE);
    }

    private void executeStatement(Statement statement, String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement createStatement() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
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
