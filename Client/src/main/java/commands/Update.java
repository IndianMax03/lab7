package commands;

import client.ClientReceiver;
import listening.Request;

import java.util.Optional;

public class Update extends ClientCommand {

    public Update(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println("Команда update_id требует аргумент id типа long.");
            System.out.println("id не может быть отрицательным числом или нулём.");
            return Optional.empty();
        }
        return clientReceiver.update(arg);
    }
}
