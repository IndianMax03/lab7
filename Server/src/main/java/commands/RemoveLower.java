package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class RemoveLower extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public RemoveLower(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeLower(arg.getCity(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return "Введите remove_lower {element}, чтобы удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
