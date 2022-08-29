package commands;

import listening.Request;

import java.util.Optional;

public class Exit extends ClientCommand {
    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println("Команда exit не принимает аргументы.");
            return Optional.empty();
        }
        System.out.println("Спасибо за работу. До свидания.");
        System.exit(1);
        return Optional.empty();
    }
}
