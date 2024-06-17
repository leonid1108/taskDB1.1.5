package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Query;

public class UserDaoHibernateImpl implements UserDao {

    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS UserDB" +
            "( id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
            "name VARCHAR(50) NOT NULL," +
            "lastName VARCHAR(50) NOT NULL," +
            "age TINYINT NOT NULL);";
    private static final String dropTableSQL = "DROP TABLE IF EXISTS UserDB";
    private static final String readAllUsersHQL = "FROM User";
    private static final String cleanTableSQL = "DELETE FROM UserDB";

    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(dropTableSQL).executeUpdate();;
            session.getTransaction().commit();
            System.out.println("Таблица успешно удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

            System.out.println("Запись с id = " + id + " успешно удалена");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            users = session.createQuery(readAllUsersHQL, User.class).getResultList();;
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(cleanTableSQL).executeUpdate();

            System.out.println("Таблица очищена");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
