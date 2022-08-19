package server;

import base.City;
import commands.ServerCommand;
import listening.Response;

import java.time.ZonedDateTime;
import java.util.*;

public class ServerReceiver {

	private SortedSet<City> collection = new TreeSet<>();
	private final ZonedDateTime creationDate;
	private final DataBaseReceiver dbReceiver = DataBaseReceiver.getInstance();

	public ServerReceiver() {
		this.creationDate = ZonedDateTime.now();
	}

	public Response add(City city, String login) {
		if (dbReceiver.add(city, login)){
			collection.add(city);
			return new Response("Город успешно добавлен.");
		} else {
			return new Response("Город добавить не удалось коллекция не предполагает хранение городов с одинаковым именем.");
		}
	}

	public Response addIfMin(City city, String login){
		collection = dbReceiver.getActualCollection();
		if (collection.first().compareTo(city) > 0){
			dbReceiver.add(city, login);
			return new Response("Ваш элемент добавлен в коллекцию. Условие выполнено.");
		}
		return new Response("Ваш элемент не добавлен в коллекцию. Условие не выполнено.");
	}

	public Response filterStartsWithName(String name){
		collection = dbReceiver.getActualCollection();
		return new Response(
				collection.stream()
				.filter(city -> city.getName().startsWith(name))
				.map(City::toUser)
				.toArray(String[]::new)
		);
	}

	public Response info(){
		collection = dbReceiver.getActualCollection();
		String[] information = new String[3];
		information[0] = "Тип коллекции: " + collection.getClass();
		information[1] = "Дата инициализации коллекции: " + creationDate;
		information[2] = "Количество элементов коллекции: " + collection.size();
		return new Response(information);
	}

	public Response show(){
		collection = dbReceiver.getActualCollection();
		if (collection == null){
			return new Response("Ошибка при выгрузке коллекции из базы данных.");
		}
		return new Response(
				collection.stream()
				.map(City::toUser)
				.toArray(String[]::new)
		);
	}

	public Response printDescending(){
		collection = dbReceiver.getActualCollection();
		return new Response(
				collection.stream()
				.sorted(Collections.reverseOrder())
				.map(City::toUser)
				.toArray(String[]::new)
		);
	}

	public Response help(Map<String, ServerCommand> commandMap) {
		return new Response(
				commandMap.values()
						.stream()
						.map(ServerCommand::getHelp)
						.toArray(String[]::new)
		);
	}

	public Response clear(String login){
		if (dbReceiver.clear(login)) {
			collection.removeIf(city -> city.getLogin().equals(login));
			return new Response("Из коллекции удалены все, созданные вами города.");
		}
		return new Response("Не удалось выполнить удаление.");
	}

	public Response authorization(String login, String password){
		if (login.isEmpty()){
			return new Response("Имя пользователя не может быть пустой строкой.");
		}
		return dbReceiver.authorization(login, password);
	}

	void initCollection(){
		collection = dbReceiver.getActualCollection();
	}
}
