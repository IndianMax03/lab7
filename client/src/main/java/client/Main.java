package client;

import java.util.Locale;

public class Main {

    public static Locale locale = Locale.getDefault();

    public static void main(String[] args) {

        Client client = new Client();
        Terminal terminal = new Terminal(client);
        terminal.startApp();

    }
}
