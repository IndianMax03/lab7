package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class Show extends ClientCommand {
    public Show(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда show не принимает аргументы.");
            return Optional.empty();
        }
        return Optional.of(new Request("show"));
    }
}
