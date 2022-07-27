package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(16) NOT NULL," +
            "lastName VARCHAR(32) NOT NULL," +
            "age TINYINT UNSIGNED);";
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users;";
    public static final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (:name, :lastName,  :age);";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id = :id";
    public static final String GET_ALL_USERS = "SELECT id, name, lastName, age FROM users";
    public static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";

    private Session session;

    public UserDaoHibernateImpl() {

    }

    public UserDaoHibernateImpl(Session session) {
        this.session = session;
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(CREATE_USERS_TABLE, User.class).executeUpdate();
        transaction.commit();

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(DROP_USERS_TABLE, User.class).executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(SAVE_USER, User.class)
                .setParameter("name", name)
                .setParameter("lastName", lastName)
                .setParameter("age", age)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(REMOVE_USER, User.class)
                .setParameter("id", id)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createNativeQuery(GET_ALL_USERS, User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(CLEAN_USERS_TABLE, User.class).executeUpdate();
        transaction.commit();
    }
}
