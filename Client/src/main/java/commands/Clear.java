package commands;

import listening.Request;

import java.util.Optional;

public class Clear extends ClientCommand {
    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда clear не принимает аргументы.");
            return Optional.empty();
        }
        return Optional.of(new Request("clear"));
    }

}
