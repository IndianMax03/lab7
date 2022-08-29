package service;

import base.City;
import businessLogic.Database;
import daoPattern.CityDAO;

import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityService extends Database implements CityDAO {

    private final Connection connection = getConnection();
    private final Logger logger = Logger.getAnonymousLogger();

    public CityService() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQLCity.INIT.QUERY);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при создании таблицы cities");
            System.exit(-1);
        }
    }

    @Override
    public int create(City city, String login) {
        int result = -1;
        try (PreparedStatement statement = connection.prepareStatement(CityService.SQLCity.INSERT.QUERY)) {
            statement.setString(1, city.getName());
            statement.setDouble(2, city.getCoordinates().getX());
            statement.setDouble(3, city.getCoordinates().getY());
            statement.setTimestamp(4, city.getCreationDate());
            statement.setFloat(5, city.getArea());
            statement.setInt(6, city.getPopulation());
            statement.setFloat(7, city.getMetersAboveSeaLevel());
            statement.setString(8, city.getClimate().toString());
            statement.setString(9, city.getGovernment().toString());
            statement.setString(10, city.getStandardOfLiving().toString());
            statement.setString(11, city.getGovernor().toString());
            statement.setInt(12, city.getGovernor().getHeight());
            statement.setTimestamp(13, city.getGovernor().getBirthday());
            statement.setString(14, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("id");
                return result;
            } else {
                return result;
            }
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при добавлении города.");
        }
        return result;
    }

    @Override
    public SortedSet<City> readAll() {
        SortedSet<City> result = new TreeSet<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.READ_ALL.QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                Date crDate = resultSet.getDate("creation_date");
                float area = resultSet.getFloat("area");
                int population = resultSet.getInt("population");
                float meters = resultSet.getFloat("meters_above_sea_level");
                String climate = resultSet.getString("climate");
                String government = resultSet.getString("government");
                String standard = resultSet.getString("standard_of_living");
                String governor = resultSet.getString("governor");
                int governor_height = resultSet.getInt("governor_height");
                Date governor_birthday = resultSet.getDate("governor_birthday");
                String login = resultSet.getString("login");
                City city = null;
                try {
                    city = new City(id, name, x, y, crDate, area, population, meters, climate, government, standard,
                            governor, governor_height, governor_birthday, login);
                } catch (NullPointerException ex) {
                    logger.log(Level.WARNING, "В базе данных обнаружен невалидный город. Он немедленно будет удален.");
                    deleteInvalidCity(name);
                }
                if (city != null && !result.add(city)) {
                    logger.warning(
                            "В базе данных обнаружены города с одинаковым названием. Они немедленно будут удалены.");
                    deleteInvalidCity(name);
                }
            }
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при чтении содержимого.");
            return new TreeSet<>();
        }
        return result;
    }

    private void deleteInvalidCity(String name) {
        try {
            PreparedStatement warningStatement = connection.prepareStatement(SQLCity.REMOVE_BY_NAME.QUERY);
            warningStatement.setString(1, name);
            warningStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при удалении невалидного города из базы данных.");
        }
    }

    @Override
    public boolean updateById(int id, City city, String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.UPDATE_BY_ID.QUERY)) {
            statement.setString(1, city.getName());
            statement.setDouble(2, city.getCoordinates().getX());
            statement.setDouble(3, city.getCoordinates().getY());
            statement.setTimestamp(4, city.getCreationDate());
            statement.setFloat(5, city.getArea());
            statement.setInt(6, city.getPopulation());
            statement.setFloat(7, city.getMetersAboveSeaLevel());
            statement.setString(8, city.getClimate().toString());
            statement.setString(9, city.getGovernment().toString());
            statement.setString(10, city.getStandardOfLiving().toString());
            statement.setString(11, city.getGovernor().toString());
            statement.setInt(12, city.getGovernor().getHeight());
            statement.setTimestamp(13, city.getGovernor().getBirthday());
            statement.setInt(14, id);
            statement.setString(15, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при обновлении города.");
        }
        return result;
    }

    @Override
    public boolean removeById(int id, String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.REMOVE_BY_ID.QUERY)) {
            statement.setInt(1, id);
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при удалении города по его id.");
        }
        return result;
    }

    @Override
    public boolean clearByUser(String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.CLEAR_BY_USER.QUERY)) {
            statement.setString(1, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при очистке коллекции пользователем.");
        }
        return result;
    }

    @Override
    public boolean removeAllByGovernment(String government, String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.REMOVE_ALL_BY_GOVERNMENT.QUERY)) {
            statement.setString(1, government);
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при очистке коллекции пользователем.");
        }
        return result;
    }

    @Override
    public boolean removeGreater(City city, String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.REMOVE_GREATER.QUERY)) {
            statement.setString(1, city.getName());
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при удалению городов больших, чем заданный.");
        }
        return result;
    }

    @Override
    public boolean removeLower(City city, String login) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLCity.REMOVE_LOWER.QUERY)) {
            statement.setString(1, city.getName());
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            logger.warning("Ошибка при обращении к базе данных при удалению городов меньших, чем заданный.");
        }
        return result;
    }

    private enum SQLCity {

        INSERT("insert into cities (name, x, y, creation_date, area, population, meters_above_sea_level, climate, "
                + "government, standard_of_living, governor, governor_height, governor_birthday, login) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning id;"),
        READ_ALL("select * from cities;"), REMOVE_BY_NAME("delete from cities where name = ?;"),
        UPDATE_BY_ID("update cities set name = ?, x = ?, y = ?, creation_date = ?, area = ?, population = ?, "
                + "meters_above_sea_level = ?, climate = ?, government = ?, standard_of_living = ?, governor = ?, "
                + "governor_height = ?, governor_birthday = ? where id = ? and login = ? returning id;"),
        REMOVE_BY_ID("delete from cities where id = ? and login = ? returning id;"),
        CLEAR_BY_USER("delete from cities where login = ? returning id;"),
        REMOVE_ALL_BY_GOVERNMENT("delete from cities where government = ? and login = ? returning id;"),
        REMOVE_GREATER("delete from cities where name > ? and login = ? returning id;"),
        REMOVE_LOWER("delete from cities where name < ? and login = ? returning id;"),
        INIT("create table if not exists cities(id serial not null primary key, name varchar(50) not null unique, "
                + "x double precision not null, y double precision not null, creation_date date, area float, population integer, "
                + "meters_above_sea_level float, climate varchar(23), government varchar(13), standard_of_living varchar(13), "
                + "governor varchar(18), governor_height integer, governor_birthday date, login varchar(20), "
                + "foreign key (login) references users (login));");

        String QUERY;

        SQLCity(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
