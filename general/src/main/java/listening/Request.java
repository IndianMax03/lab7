package listening;

import base.City;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Locale;

public class Request implements Serializable {

    private static final long serialVersionUID = 2837967881502539406L;

    private final String commandName;
    private final String argument;
    private final City city;
    private SocketAddress clientAddres = null;
    private String login;
    private String password;
    private Locale locale;

    public Request(String commandName, String argument, City city) {
        this.commandName = commandName;
        this.argument = argument;
        this.city = city;
    }

    public Request(String commandName, City city) {
        this(commandName, null, city);
    }

    public Request(String commandName, String argument) {
        this(commandName, argument, null);
    }

    public Request(String commandName) {
        this(commandName, null, null);
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public City getCity() {
        return city;
    }

    public SocketAddress getClientAddres() {
        return clientAddres;
    }

    public void setClientAddres(SocketAddress clientAddres) {
        this.clientAddres = clientAddres;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
