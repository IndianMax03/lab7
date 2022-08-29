package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Add extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Add(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request request) {
        return Optional.of(serverReceiver.add(request.getCity(), request.getLogin()));
    }

    @Override
    public String getHelp() {
        return "Введите add {element}, чтобы добавить новый элемент в коллекцию";
    }
}