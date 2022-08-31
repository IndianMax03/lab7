package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class AddIfMin extends ClientCommand {

    public AddIfMin(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда add_if_min не принимает аргументы.");
            return Optional.empty();
        }
        return clientReceiver.addIfMin();
    }
}
