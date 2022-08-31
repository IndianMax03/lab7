package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Clear extends ServerCommand {

    public Clear(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.clear(arg.getLogin()));
    }

    public String getHelp() {
        return "Введите clear, чтобы очистить коллекцию";
    }
}
