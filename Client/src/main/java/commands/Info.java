package commands;

import listening.Request;

import java.util.Optional;

public class Info extends ClientCommand {
    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда info не принимает аргументы.");
            return Optional.empty();
        }
        return Optional.of(new Request("info"));
    }
}
