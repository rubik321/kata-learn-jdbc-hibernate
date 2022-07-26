package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        String testName = "Ivan";
        String testLastName = "Ivanov";
        byte testAge = 5;

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser(testName, testLastName, testAge);
        userService.getAllUsers().forEach(Main::printUser);
        userService.removeUserById(3);
        userService.getAllUsers().forEach(Main::printUser);
        userService.cleanUsersTable();
        System.out.println(userService.getAllUsers().size());

    }

    private static void printUser(User u) {
        System.out.println(u.getName() + ", " + u.getLastName() + ", " + u.getAge());
    }
}
