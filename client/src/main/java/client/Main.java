package client;

import clientLogger.ClientLogger;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
        } catch (NoSuchElementException ignore) {
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, RB.getString("badPort"));
        } finally {
            LOGGER.info(RB.getString("stop"));
        }
    }
}
