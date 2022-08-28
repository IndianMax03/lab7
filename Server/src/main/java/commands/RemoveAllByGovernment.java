package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class RemoveAllByGovernment extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public RemoveAllByGovernment(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Response execute(Request arg) {
        return serverReceiver.removeAllByGovernment(arg.getArgument(), arg.getLogin());
    }

    @Override
    public String getHelp() {
        return "Введите remove_all_by_government government, чтобы удалить из коллекции все элементы, значение поля"
                + " government которого эквивалентно заданному";
    }
}
