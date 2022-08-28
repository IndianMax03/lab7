package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class Authorization extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Authorization(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Response execute(Request arg) {
        return serverReceiver.authorization(arg.getLogin(), arg.getPassword());
    }

    @Override
    public String getHelp() {
        return "Введите authorization, чтобы авторизоваться в системе.";
    }
}
