package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class FilterStartsWithName extends ServerCommand {

    public FilterStartsWithName(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.filterStartsWithName(arg.getArgument()));
    }

    @Override
    public String getHelp() {
        return "Введите filter_starts_with_name name, чтобы вывести элементы, значение поля name которых начинается с "
                + "заданной подстроки";
    }
}
