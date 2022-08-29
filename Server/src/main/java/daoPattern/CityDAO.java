package daoPattern;

import base.City;

import java.util.SortedSet;

public interface CityDAO {

    int create(City city, String login);

    SortedSet<City> readAll();

    boolean updateById(int id, City city, String login);

    boolean clearByUser(String login);

    boolean removeAllByGovernment(String government, String login);

    boolean removeById(int id, String login);

    boolean removeGreater(City city, String login);

    boolean removeLower(City city, String login);

}
