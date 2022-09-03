package businessLogic;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/laba7";
    private static final String DB_USER = "labber";
    private static final String DB_PASSWORD = "laba";
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected Database() {
    }

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Не удалось установить соединение с базой данных", new RuntimeException());
        }
        return connection;
    }

    protected static void closeConnection(Connection connection) {
        try {
            DbUtils.close(connection);
        } catch (SQLException throwables) {
            LOGGER.warning("Не удалось закрыть подключение");
        }
    }

    protected static void closeStatement(PreparedStatement statement) {
        try {
            DbUtils.close(statement);
        } catch (SQLException throwables) {
            LOGGER.warning("Не удалось закрыть состояние statement");
        }
    }

}
