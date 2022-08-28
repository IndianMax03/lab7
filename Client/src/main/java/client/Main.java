package client;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        ClientReceiver clientReceiver = new ClientReceiver();
        ClientInvoker invoker = new ClientInvoker(clientReceiver);
        Terminal terminal = new Terminal(invoker, client);
        try {
            terminal.startKeyboard();
        } catch (NoSuchElementException ex) {
            System.exit(1);
        }
    }
}
