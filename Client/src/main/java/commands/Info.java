package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class Info extends ClientCommand {
    public Info(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда info не принимает аргументы.");
            return Optional.empty();
        }
        return Optional.of(new Request("info"));
    }
}
