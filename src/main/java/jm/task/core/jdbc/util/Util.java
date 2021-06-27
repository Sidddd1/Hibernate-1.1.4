package jm.task.core.jdbc.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/1.1.3?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "xpnkcv5rt12";
    private static Util instance;
    private static Connection connection;
    private static Util session;

    private Util() {

    }

    public static Util getInstance() {

        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public static Util getSession(){
        if (session == null) {
            session = new Util();
        }
        return session;
    }

    public Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName(DRIVER);
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("Соединение с БД не установлено");
                    e.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        Properties prop = new Properties();
        prop.put(Environment.DRIVER, DRIVER);
        prop.put(Environment.URL, URL);
        prop.put(Environment.USER, USER);
        prop.put(Environment.PASS, PASSWORD);
        //prop.put(Environment.CONNECTION_PROVIDER, "com.zaxxer.hikari.hibernate.HikariConnectionProvider");
        prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
        prop.put(Environment.SHOW_SQL, "true");
        prop.put(Environment.HBM2DDL_AUTO, "none");

        Configuration config = new Configuration();
        config.setProperties(prop);
        config.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();
        try {
            sessionFactory = (SessionFactory) config.buildSessionFactory(registry);
            System.out.println("SessionFactory - создан");
        } catch (Exception e) {
            System.out.println("SessionFactory - НЕ создан");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return sessionFactory;
    }

}



