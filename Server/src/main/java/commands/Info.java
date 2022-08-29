package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Info extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Info(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.info());
    }

    @Override
    public String getHelp() {
        return "Введите info, чтобы вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации,"
                + " количество элементов)";
    }
}
