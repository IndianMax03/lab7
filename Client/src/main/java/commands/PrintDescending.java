package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class PrintDescending extends ClientCommand {
    public PrintDescending(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда print_descending не принимает аргументы.");
            return Optional.empty();
        }
        return Optional.of(new Request("print_descending"));
    }
}
