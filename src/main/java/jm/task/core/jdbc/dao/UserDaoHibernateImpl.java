package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("create table if not exists Users (" +
                    "ID bigint primary key auto_increment," +
                    "Name varchar(15) not null," +
                    "LastName varchar(15)," +
                    "Age tinyint)").executeUpdate();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("drop table if exists Users").executeUpdate();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                } catch (Exception e) {

                }
            }
        }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                } catch (Exception e) {

                }
            }
        }


    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            User u = new User();
            u.setId(id);
            session.delete(u);
            session.getTransaction().commit();
            System.out.println("User с ID – " + id + " удалён");
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                } catch (Exception e) {

                }
            }
        }


    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> list = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            list = session.createQuery("from User").list();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                } catch (Exception e) {

                }
            }
            return list;
        }


    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("truncate table users");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                } catch (Exception e) {

                }
            }
        }


    @Override
    public List<User> getPeopleOlderThan(byte age) {
        return null;
    }
}
