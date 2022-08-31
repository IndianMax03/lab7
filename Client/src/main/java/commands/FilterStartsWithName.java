package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class FilterStartsWithName extends ClientCommand {

    public FilterStartsWithName(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println("Команда filter_starts_with_name принимает требует аргумент name.");
            return Optional.empty();
        }
        return Optional.of(new Request("filter_starts_with_name", arg));
    }
}
