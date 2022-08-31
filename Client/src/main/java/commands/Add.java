package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class Add extends ClientCommand {

    public Add(ClientReceiver clientReceiver) {
        super(clientReceiver);
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
