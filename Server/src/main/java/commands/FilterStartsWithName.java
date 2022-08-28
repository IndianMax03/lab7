package commands;

import listening.Request;
import listening.Response;
import server.ServerReceiver;

public class FilterStartsWithName extends ServerCommand {

    private final ServerReceiver serverReceiver;

    public FilterStartsWithName(ServerReceiver serverReceiver) {
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Response execute(Request arg) {
        return serverReceiver.filterStartsWithName(arg.getArgument());
    }

    @Override
    public String getHelp() {
        return "Введите filter_starts_with_name name, чтобы вывести элементы, значение поля name которых начинается с "
                + "заданной подстроки";
    }
}
