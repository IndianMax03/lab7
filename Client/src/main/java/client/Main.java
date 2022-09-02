package client;

import clientLogger.ClientLogger;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = ClientLogger.getLogger();

    public static void main(String[] args) {
        try {
            Client client = new Client();
            ClientReceiver clientReceiver = new ClientReceiver();
            ClientInvoker invoker = new ClientInvoker(clientReceiver);
            Terminal terminal = new Terminal(invoker, client);
            terminal.startKeyboard();
        } catch (NoSuchElementException ex) {
            LOGGER.info("Клиентский модуль завершил работу.");
        }
    }
}
