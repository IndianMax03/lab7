package server;

import base.City;
import listening.Response;

import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class DataBaseReceiver {

	Connection connection;
	private static DataBaseReceiver instance;

	private DataBaseReceiver(){
		try{
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laba7",
					"labber", "laba");
			PreparedStatement statement = connection.prepareStatement(
					"create table if not exists users(" +
							"login varchar(20) not null primary key," +
							"password varchar(20)" +
							");"+
							"create table if not exists cities(" +
							"id serial not null primary key," +
							"name varchar(50) not null unique," +
							"x double precision not null," +
							"y double precision not null," +
							"creation_date date," +
							"area float," +
							"population integer," +
							"meters_above_sea_level float," +
							"climate varchar(23)," +
							"government varchar(13)," +
							"standard_of_living varchar(13)," +
							"governor varchar(18)," +
							"governor_height integer," +
							"governor_birthday date," +
							"login varchar(20),"+
							"foreign key (login) references users (login)" +
							");");
			statement.executeUpdate();
			System.out.println("База подгружена, всё ок =)");
		} catch (SQLException ex){
			System.out.println("Ошибка при подключении сервера к базе данных. Сервер не может начать работу.");
			System.exit(-1);
		}
	}

	public boolean add(City city,String login){
		try {
			PreparedStatement statement = connection.prepareStatement("insert into cities (name, x, y, creation_date, " +
					"area, population, meters_above_sea_level, climate, government, standard_of_living, governor, " +
					"governor_height, governor_birthday, login)" +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning id;");
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
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				city.setLogin(login);
				city.setId(result.getInt(1));
				return true;
			}
			return false;
		} catch (SQLException ex) {
			return false;
		}
	}

	public boolean clear(String login){
		try{
			PreparedStatement statement = connection.prepareStatement("delete from cities where login = ?;");
			statement.setString(1, login);
			statement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
		return true;
	}

	public Response authorization(String login, String password){
		if (login.length() > 20 || password.length() > 20) {
			return new Response("Длины логина и пароля не могут превышать 20 символов.");
		}
		try {
			PreparedStatement statement = connection.prepareStatement("select password from users where login = ?;");
			statement.setString(1, login);
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String dbPassword = result.getString("password");
				if (password.equals(dbPassword)){
					return new Response("");
				} else {
					return new Response("Указан неверный пароль для пользователя " + login);
				}
			} else {
				statement = connection.prepareStatement("insert into users (login, password) values (?, ?);");
				statement.setString(1, login);
				statement.setString(2, password);
				statement.executeUpdate();
				return new Response("");
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return new Response("Непредвиденная ошибка добавления пользователя.");
		}
	}

	public SortedSet<City> getActualCollection(){
		SortedSet<City> collection = new TreeSet<>();
		try {
			PreparedStatement statement = connection.prepareStatement("select * from cities;");
			ResultSet result = statement.executeQuery();
			while (result.next()){
				int id = result.getInt(1);
				String name = result.getString("name");
				double x = result.getDouble("x");
				double y = result.getDouble("y");
				Date crDate = result.getDate("creation_date");
				float area = result.getFloat("area");
				int population = result.getInt("population");
				float meters = result.getFloat("meters_above_sea_level");
				String climate = result.getString("climate");
				String government = result.getString("government");
				String standard = result.getString("standard_of_living");
				String governor = result.getString("governor");
				int governor_height = result.getInt("governor_height");
				Date governor_birthday = result.getDate("governor_birthday");
				String login = result.getString("login");
				City city = null;
				try {
					city = new City(id, name, x, y, crDate, area, population, meters, climate, government, standard,
						governor, governor_height, governor_birthday, login);
				} catch (NullPointerException ex){
					System.out.println("ВНИМАНИЕ. В БАЗЕ ДАННЫХ ОБНАРУЖЕН НЕВАЛИДНЫЙ ГОРОД. ОН НЕМЕДЛЕННО БУДЕТ УДАЛЁН.");
					statement = connection.prepareStatement("delete from cities where name = ?");
					statement.setString(1, name);
					statement.executeUpdate();
				}
				if (city != null && !collection.add(city)){
					System.out.println("ВНИМАНИЕ. В БАЗЕ ДАННЫХ ОБНАРУЖЕН ГОРОД, НАРУШАЮЩИЙ УСЛОВИЕ ПОЛЬЗОВАНИЯ КОЛЛЕКЦИЕЙ. " +
							"ПРИЧИНА: ПОВТОР. ОН НЕМЕДЛЕННО БУДЕТ УДАЛЁН.");
					statement = connection.prepareStatement("delete from cities where name = ?");
					statement.setString(1, name);
					statement.executeUpdate();
				}
			}
		} catch (SQLException ex){
			System.out.println("При выгрузке коллекции из базы данных произошла ошибка.");
			return null;
		}
		return collection;
	}

	public static DataBaseReceiver getInstance() {
		if (instance == null){
			return new DataBaseReceiver();
		}
		return instance;
	}
}