package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class RemoveLower extends ClientCommand {

    private final ClientReceiver clientReceiver;

    public RemoveLower(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда remove_lower не принимает аргумент.");
            return Optional.empty();
        }
        return clientReceiver.removeLower();
    }
}
