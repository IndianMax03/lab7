package server;

import base.City;
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
    private final ResourceBundle RB = ResourceBundle.getBundle("server");
    private final ZonedDateTime creationDate;
    private final CityService cityService = new CityService();
    private final UserService userService = new UserService();

    public ServerReceiver() {
        this.creationDate = ZonedDateTime.now();
    }

    public Response add(City city, String login) {
        collectionLock.lock();
        try {
            int id = cityService.create(city, login);
            if (id > 0) {
                city.setId(id);
                city.setLogin(login);
                collection.add(city);
                return new Response(RB.getString("cityAdded") + ", id = " + id);
            } else {
                return new Response(RB.getString("addingEx") + ":\n" + RB.getString("sameNames"));
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
                city.setId(id);
                city.setLogin(login);
                collection.add(city);
                return new Response(RB.getString("cityAdded") + ", id = " + id);
            }
            return new Response(RB.getString("addingEx") + ":\n" + RB.getString("failedCond"));
        } catch (NoSuchElementException ex) {
            return add(city, login);
        } finally {
            collectionLock.unlock();
        }
    }

    public Response filterStartsWithName(String name) {
        collectionLock.lock();
        try {
            return new Response(collection.stream().filter(city -> city.getName().startsWith(name)).map(City::toString)
                    .toArray(String[]::new));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response info() {
        collectionLock.lock();
        try {
            String[] information = new String[3];
            information[0] = RB.getString("type") + ": " + collection.getClass();
            information[1] = RB.getString("date") + ": " + creationDate;
            information[2] = RB.getString("size") + ": " + collection.size();
            return new Response(information);
        } finally {
            collectionLock.unlock();
        }
    }

    public Response show() {
        collectionLock.lock();
        try {
            return new Response(collection.stream().map(City::toString).toArray(String[]::new));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response printDescending() {
        collectionLock.lock();
        try {
            return new Response(
                    collection.stream().sorted(Collections.reverseOrder()).map(City::toString).toArray(String[]::new));
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
                return new Response(RB.getString("niceRemoving") + ":\n" + "'government' = " + government);
            } else {
                return new Response(RB.getString("failedRemoving") + ":\n" + RB.getString("noOne"));
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
                return new Response("???????????? ?????????????? ???????????????????? id.");
            }
            if (cityService.removeById(id, login)) {
                collection.removeIf(city -> city.getId().equals(id));
                return new Response(RB.getString("niceRemoving") + ":\n" + "id = " + id);
            } else {
                return new Response(RB.getString("failedRemoving") + ":\n" + RB.getString("noOne") + " "
                        + RB.getString("or") + " " + RB.getString("notYour"));
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
                        && cityFromColl.getLogin().equals(login));
                return new Response(RB.getString("niceRemoving") + ":\n" + RB.getString("greater") + ".");
            }
            return new Response(RB.getString("noOne") + ":\n" + RB.getString("greater") + ".");
        } finally {
            collectionLock.unlock();
        }
    }

    public Response removeLower(City city, String login) {
        collectionLock.lock();
        try {
            if (cityService.removeLower(city, login)) {
                collection.removeIf(cityFromColl -> cityFromColl.compareTo(city) < 0
                        && cityFromColl.getLogin().equals(login));
                return new Response(RB.getString("niceRemoving") + ":\n" + RB.getString("lower") + ".");
            }
            return new Response(RB.getString("noOne") + ":\n" + RB.getString("lower") + ".");
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
                return new Response(RB.getString("clearNice"));
            }
            return new Response(RB.getString("clearBad"));
        } finally {
            collectionLock.unlock();
        }
    }

    public Response update(String idStr, City city, String login) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            return new Response("???????????? ?????????????? ???????????????????? id.");
        }
        if (cityService.updateById(id, city, login)) {
            collection.removeIf(cityFromColl -> cityFromColl.getId().equals(id));
            city.setId(id);
            city.setLogin(login);
            collection.add(city);
            return new Response(RB.getString("updNice"));
        } else {
            return new Response(RB.getString("updBad") + ":\n" + RB.getString("noOne") + " " + RB.getString("or") + " "
                    + RB.getString("notYour"));
        }
    }

    public Response authorization(String login, String password) {
        if (login.isEmpty()) {
            return new Response(RB.getString("emptyLine"));
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
                return new Response(RB.getString("badPass") + ": " + login);
            } else {
                userService.create(login, password);
                return new Response("");
            }
        } finally {
            usersLock.unlock();
        }
    }

    void initCollection() {
        collection = cityService.readAll();
    }

}
