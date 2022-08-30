package service;

import businessLogic.Database;
import daoPattern.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserService extends Database implements UserDAO {

    private final Logger logger = Logger.getAnonymousLogger();
    private Connection connection = null;
    private PreparedStatement statement = null;

    public UserService() {
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLUser.INIT.QUERY);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при создании таблицы users");
            System.exit(-1);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean create(String login, String password) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLUser.INSERT.QUERY);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при добавлении пользователя.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean checkExists(String login, String password) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLUser.CHECK_EXISTS.QUERY);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при проверке пользователя.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean checkImpostor(String login, String password) {
        boolean result = true;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLUser.CHECK_IMPOSTOR.QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                result = !dbPassword.equals(password);
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при проверке пользователя.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean remove(String login, String password) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLUser.REMOVE.QUERY);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при удалении пользователя.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    private enum SQLUser {

        INSERT("insert into users (login, password) values (?, ?) returning login;"),
        CHECK_EXISTS("select * from users where login = ? and password = ?;"),
        CHECK_IMPOSTOR("select * from users where login = ?;"),
        REMOVE("delete from users where login = ? and password = ? returning login;"),
        INIT("create table if not exists users(login varchar(20) not null primary key, password varchar(150));");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
