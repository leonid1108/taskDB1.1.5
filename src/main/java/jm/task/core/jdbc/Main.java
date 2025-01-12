package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Util.getSessionFactory();

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("Леонид", "Лушников", (byte) 21);
        userServiceImpl.saveUser("Саня", "Пупкин", (byte) 18);
        userServiceImpl.saveUser("Ваня", "Опилкин", (byte) 20);
        userServiceImpl.saveUser("Гоша", "Шляпочкин", (byte) 22);

        userServiceImpl.removeUserById(2);

        List<User> list = userServiceImpl.getAllUsers();
        System.out.println(list);

        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();

    }
}
