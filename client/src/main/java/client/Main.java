package client;

import clientLogger.ClientLogger;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = ClientLogger.getLogger();
    private static final ResourceBundle RB = ResourceBundle.getBundle("client");

    public static void main(String[] args) {

        Client client = new Client();
        Terminal terminal = new Terminal(client);
        terminal.startApp();

    }
}
