package server;

import base.City;
import businessLogic.Database;
import commands.ServerCommand;
import listening.Response;
import service.CityService;
import service.UserService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ServerReceiver {

    private static final ReentrantLock collectionLock = new ReentrantLock();
    private static final ReentrantLock usersLock = new ReentrantLock();
    private SortedSet<City> collection = new TreeSet<>();
    private final ZonedDateTime creationDate;
    private static final CityService cityService = new CityService();
    private static final UserService userService = new UserService();

    public ServerReceiver() {
        this.creationDate = ZonedDateTime.now();
    }

    public Response add(City city, String login) {
        collectionLock.lock();
        try {
            int id = cityService.create(city, login);
            if (id > 0) {
                collection.add(city);
                return new Response("Город успешно добавлен. Его id = " + id);
            } else {
                return new Response(
                        "Город добавить не удалось коллекция не предполагает хранение городов с одинаковым именем.");
            }
        } finally {
            collectionLock.unlock();
        }
    }

    public Response addIfMin(City city, String login) {
        collectionLock.lock();
        try {
            if (collection.first().compareTo(city) > 0) {
                int id = cityService.create(city, login);
                collection.add(city);
                return new Response("Ваш элемент добавлен в коллекцию. Его id = " + id);
            }
            return new Response("Ваш элемент не добавлен в коллекцию. Условие не выполнено.");
        } finally {
            collectionLock.unlock();
        }
    }

    public Response filterStartsWithName(String name) {
        collectionLock.lock();
        try {
            return new Response(collection.stream().filter(city -> city.getName().startsWith(name)).map(City::toUser)
                    .toArray(String[]::new));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response info() {
        collectionLock.lock();
        try {
            String[] information = new String[3];
            information[0] = "Тип коллекции: " + collection.getClass();
            information[1] = "Дата инициализации коллекции: " + creationDate;
            information[2] = "Количество элементов коллекции: " + collection.size();
            return new Response(information);
        } finally {
            collectionLock.unlock();
        }
    }

    public Response show() {
        collectionLock.lock();
        try {
            return new Response(collection.stream().map(City::toUser).toArray(String[]::new));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response printDescending() {
        collectionLock.lock();
        try {
            return new Response(
                    collection.stream().sorted(Collections.reverseOrder()).map(City::toUser).toArray(String[]::new));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response removeAllByGovernment(String government, String login) {
        collectionLock.lock();
        try {
            if (cityService.removeAllByGovernment(government, login)) {
                collection.removeIf(
                        city -> city.getGovernment().toString().equals(government) && city.getLogin().equals(login));
                return new Response("Из коллекции удалены все ваши города с полем government = " + government);
            } else {
                return new Response("Удаление выполнить не удалось. Вы еще не создали города с данным условием.");
            }
        } finally {
            collectionLock.unlock();
        }
    }

    public Response removeById(String idStr, String login) {
        collectionLock.lock();
        try {
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                return new Response("Клиент передал невалидный id.");
            }
            if (cityService.removeById(id, login)) {
                collection.removeIf(city -> city.getId().equals(id));
                return new Response("Ваш элемент с id = " + id + " успешно удален из коллекции.");
            } else {
                return new Response("Элемента с таким id не существует или он вам не принадлежит.");
            }
        } finally {
            collectionLock.unlock();
        }
    }

    public Response removeGreater(City city, String login) {
        collectionLock.lock();
        try {
            if (cityService.removeGreater(city, login)) {
                collection.removeIf(cityFromColl -> cityFromColl.compareTo(city) > 0
                        && cityFromColl.getLogin().equals(city.getLogin()));
                return new Response("Из коллекции удалены ваши элементы, превышающие введённый.");
            }
            return new Response("Ни один элемент не был удалён.");
        } finally {
            collectionLock.unlock();
        }
    }

    public Response removeLower(City city, String login) {
        collectionLock.lock();
        try {
            if (cityService.removeLower(city, login)) {
                collection.removeIf(cityFromColl -> cityFromColl.compareTo(city) < 0
                        && cityFromColl.getLogin().equals(city.getLogin()));
                return new Response("Из коллекции удалены ваши элементы, меньшие, чем введённый.");
            }
            return new Response("Ни один элемент не был удалён.");
        } finally {
            collectionLock.unlock();
        }
    }

    public Response help(Map<String, ServerCommand> commandMap) {
        return new Response(commandMap.values().stream().map(ServerCommand::getHelp).toArray(String[]::new));
    }

    public Response clear(String login) {
        collectionLock.lock();
        try {
            if (cityService.clearByUser(login)) {
                collection.removeIf(city -> city.getLogin().equals(login));
                return new Response("Из коллекции удалены все, созданные вами города.");
            }
            return new Response("Ни один элемент не был удалён.");
        } finally {
            collectionLock.unlock();
        }
    }

    public Response update(String idStr, City city, String login) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            return new Response("Клиент передал невалидный id.");
        }
        if (cityService.updateById(id, city, login)) {
            collection.removeIf(cityFromColl -> cityFromColl.getId().equals(id));
            collection.add(city);
            return new Response("Поля города с id=" + id + " успешно обновлены.");
        } else {
            return new Response("Поля города с id=" + id + " обновить не удалось. Город не найден или вы не владелец.");
        }
    }

    public Response authorization(String login, String password) {
        if (login.isEmpty()) {
            return new Response("Имя пользователя не может быть пустой строкой.");
        }
        // hashing: https://clck.ru/t7zn9
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            password = hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        usersLock.lock();
        try {
            if (userService.checkExists(login, password)) {
                return new Response("");
            } else if (userService.checkImpostor(login, password)) {
                return new Response("Неверный пароль для пользователя " + login);
            } else {
                userService.create(login, password);
                return new Response("");
            }
        } finally {
            usersLock.unlock();
        }
    }

    public static boolean isUser(String login, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            password = hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        usersLock.lock();
        try {
            return userService.checkExists(login, password);
        } finally {
            usersLock.unlock();
        }
    }

    void initCollection() {
        collection = cityService.readAll();
    }

}
