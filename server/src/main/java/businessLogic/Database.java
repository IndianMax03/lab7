package businessLogic;

import org.apache.commons.dbutils.DbUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected Database() {
    }

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Scanner scanner = new Scanner(new File("auth").getAbsoluteFile());
            final String DB_URL = "jdbc:postgresql://localhost:5432/laba7"; // pg:5432/studs -> localhost:5432/laba7
            final String DB_USER = scanner.nextLine();
            final String DB_PASSWORD = scanner.nextLine();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException throwables) {
            LOGGER.log(Level.SEVERE, "Не удалось установить соединение с базой данных", new RuntimeException());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Не удалось найти файл auth с данными для подключения к бд", new RuntimeException());
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
