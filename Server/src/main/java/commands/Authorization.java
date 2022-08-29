package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Authorization extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Authorization(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.authorization(arg.getLogin(), arg.getPassword()));
    }

    @Override
    public String getHelp() {
        return "Введите authorization, чтобы авторизоваться в системе.";
    }
}
