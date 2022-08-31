package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class RemoveAllByGovernment extends ServerCommand {

    public RemoveAllByGovernment(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeAllByGovernment(arg.getArgument(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return "Введите remove_all_by_government government, чтобы удалить из коллекции все элементы, значение поля"
                + " government которого эквивалентно заданному";
    }
}
