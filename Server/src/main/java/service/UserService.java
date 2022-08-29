package service;

import businessLogic.Database;
import daoPattern.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserService extends Database implements UserDAO {

    private final Connection connection = getConnection();
    private final Logger logger = Logger.getAnonymousLogger();

    public UserService() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQLUser.INIT.QUERY);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при создании таблицы users");
            System.exit(-1);
        }
    }

    @Override
    public boolean create(String login, String password) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при добавлении пользователя.");
        }
        return result;
    }

    @Override
    public boolean check(String login, String password) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.CHECK.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при проверке пользователя.");
        }
        return result;
    }

    @Override
    public boolean remove(String login, String password) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.REMOVE.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при удалении пользователя.");
        }
        return result;
    }

    private enum SQLUser {

        INSERT("insert into users (login, password) values (?, ?) returning login;"),
        CHECK("select * from users where login = ? and password = ?;"),
        REMOVE("delete from users where login = ? and password = ? returning login;"),
        INIT("create table if not exists users(login varchar(20) not null primary key, password varchar(150));");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
