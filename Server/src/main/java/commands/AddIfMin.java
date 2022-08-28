package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class AddIfMin extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public AddIfMin(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Response execute(Request arg) {
        return serverReceiver.addIfMin(arg.getCity(), arg.getLogin());
    }

    @Override
    public String getHelp() {
        return "Введите add_if_min {element}, чтобы добавить новый элемент в коллекцию, если его значение меньше, чем у"
                + " наименьшего элемента этой коллекции";
    }

}
