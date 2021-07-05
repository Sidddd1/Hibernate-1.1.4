package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Util util = Util.getInstance();

    @Override
    public void createUsersTable() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.execute("create table if not exists Users (" +
                    "ID bigint primary key auto_increment," +
                    "Name varchar(15) not null," +
                    "LastName varchar(15)," +
                    "Age tinyint)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void dropUsersTable() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.execute("drop table if exists Users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "insert into Users (Name, LastName, Age) values (?, ?, ?)";
        Connection connection = null;
        try {
            connection = util.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete from Users where ID = ?";
        PreparedStatement preparedStatement1 = null;
        Connection connection = null;
        try {
            connection = util.getConnection();
            preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setLong(1, id);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement1 != null) {
                try {
                    preparedStatement1.close();
                } catch (SQLException throwables) {

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                User tUser = new User();
                tUser.setId(resultSet.getLong("ID"));
                tUser.setName(resultSet.getString("Name"));
                tUser.setLastName(resultSet.getString("LastName"));
                tUser.setAge(resultSet.getByte("Age"));
                list.add(tUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {

                }
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }

                } catch (Exception e) {

                }
            }
        }
        return list;

    }

    @Override
    public void cleanUsersTable() {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = util.getConnection();
            statement = connection.createStatement();
            statement.execute("truncate table Users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public List<User> getPeopleOlderThan(byte age) {
        return null;
    }
}

