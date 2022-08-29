package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class RemoveGreater extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public RemoveGreater(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда remove_greater не принимает аргумент.");
            return Optional.empty();
        }
        return clientReceiver.removeGreater();
    }
}
