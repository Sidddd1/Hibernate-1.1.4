package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();

        user.createUsersTable();

        user.saveUser("Stas", "Matveev", (byte) 28);
        user.saveUser("Ivan", "Lobanov", (byte) 28);
        user.saveUser("Kristina", "Krulova", (byte) 19);
        user.saveUser("Vanel", "Bokal", (byte) 33);

        List<User> list = user.getAllUsers();
        for (User u : list) {
            System.out.println(u.toString());
        }

        user.cleanUsersTable();

        user.dropUsersTable();
    }
}
