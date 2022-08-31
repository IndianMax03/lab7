package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class RemoveLower extends ClientCommand {

    public RemoveLower(ClientReceiver clientReceiver) {
        super(clientReceiver);
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
