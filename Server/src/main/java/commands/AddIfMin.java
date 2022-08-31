package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class AddIfMin extends ServerCommand {
    public AddIfMin(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.addIfMin(arg.getCity(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return "Введите add_if_min {element}, чтобы добавить новый элемент в коллекцию, если его значение меньше, чем у"
                + " наименьшего элемента этой коллекции";
    }

}
