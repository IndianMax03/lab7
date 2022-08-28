package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class Update extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public Update(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Response execute(Request arg) {
        return serverReceiver.update(arg.getArgument(), arg.getCity(), arg.getLogin());
    }

    @Override
    public String getHelp() {
        return "Введите update id {element}, чтобы обновить значение элемента коллекции, id которого равен заданному";
    }
}
