package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class Add extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public Add(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда add не принимает аргументы.");
            return Optional.empty();
        }
        return clientReceiver.add();
    }
}
