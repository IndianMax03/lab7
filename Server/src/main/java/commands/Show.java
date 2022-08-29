package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Show extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Show(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.show());
    }

    @Override
    public String getHelp() {
        return "Введите show, чтобы вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
