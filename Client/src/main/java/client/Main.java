package client;

import clientLogger.ClientLogger;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = ClientLogger.getLogger();
    private static final ResourceBundle RB = ResourceBundle.getBundle("client");

    public static void main(String[] args) {
        try {
            Client client = new Client();
            ClientReceiver clientReceiver = new ClientReceiver();
            ClientInvoker invoker = new ClientInvoker(clientReceiver);
            Terminal terminal = new Terminal(invoker, client);
            terminal.startKeyboard();
        } catch (NoSuchElementException ex) {
            LOGGER.info(RB.getString("stop"));
        } catch (NumberFormatException ex) {
            LOGGER.severe(RB.getString("badPort"));
        }
    }
}
