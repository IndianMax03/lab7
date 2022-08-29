package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Update extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Update(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.update(arg.getArgument(), arg.getCity(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return "Введите update id {element}, чтобы обновить значение элемента коллекции, id которого равен заданному";
    }
}
